package com.nickromero.seniorproject.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.nickromero.seniorproject.views.adapters.PaperAdapter;
import com.nickromero.seniorproject.views.adapters.QualifierAdapter;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.SuggestedPaperAdapter;
import com.nickromero.seniorproject.views.fragments.PaperController;
import com.nickromero.seniorproject.views.fragments.PaperFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.SQLConverter.SQLPaperConverter;
import data.SQLConverter.SQLPaperQualiferConverter;
import data.models.Paper;
import data.models.Qualifier;
import data.SQLConverter.SQLQualifierConverter;
import data.models.Subscription;
import data.enums.PaperType;
import data.models.XMLRoot;
import data.providers.PaperProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements QualifierDialogInterface {

    public static final String INITIAL_PAPERS = "initialPapers";
    public static final String COUNT = "count";
    private Intent createSubOrFilter;

    private final int CREATE_ACTIVITY_CODE = 1;

    private ArrayList<Qualifier> mQualifiers = new ArrayList<>();

    private GridView mGridView;

    private int[] colorArray;

    private PaperFragment mPaperFragments;

    private static PaperController mPaperController;

    private PaperAdapter test;

    private SQLQualifierConverter mQualifierConverter;

    private SQLPaperConverter mPaperConverter;

    public MainActivity mainActivity;

    private SQLPaperQualiferConverter mPaperQualifierConverter;

    private QualifierAdapter mQualifierAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mainActivity = this;
        mQualifierConverter = SQLQualifierConverter.getInstance(this);
        mPaperConverter = SQLPaperConverter.getInstance(this);
        mPaperQualifierConverter = SQLPaperQualiferConverter.getInstance(this);


        setContentView(R.layout.activity_main);


        colorArray = getResources().getIntArray(R.array.colors);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final QualifierDialogFragment qualifierDialog = QualifierDialogFragment.newInstance(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                qualifierDialog.show(getFragmentManager(), "dialog");
            }
        });


        mPaperFragments = new PaperFragment(getSupportFragmentManager(), MainActivity.this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(mPaperFragments);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        mPaperController = new PaperController().getInstance(getApplicationContext());


        mPaperController.setAdapter(new PaperAdapter(new ArrayList<Paper>(),
                        0, mPaperFragments.mSavedFragment)
                , PaperType.SAVED);
        mPaperController.setAdapter(
                new PaperAdapter(new ArrayList<Paper>(),
                        1,
                        mPaperFragments.mSubscribedFragment), PaperType.SUBSCRIBED);

        mPaperController.setSuggestedPaperAdapter(new SuggestedPaperAdapter(new ArrayList<Paper>(), mPaperFragments.mSuggestedFragment));

        mPaperController.setFragment(mPaperFragments.mSavedFragment, PaperType.SAVED);
        mPaperController.setFragment(mPaperFragments.mSubscribedFragment, PaperType.SUBSCRIBED);
        mPaperController.setFragment(mPaperFragments.mSuggestedFragment, PaperType.SUGGESTED);
        mPaperController.loadPapersFromSQLDatabase();

        mQualifiers = mQualifierConverter.getQualifiersFromDatabase();

        mQualifierAdapter = new QualifierAdapter(this, mQualifiers);
        mGridView = (GridView) findViewById(R.id.createdSubscriptions);
        mGridView.setAdapter(mQualifierAdapter);

        if (getIntent().getExtras() != null) {
            buildInitialPapers();
        }


    }

    private void buildInitialPapers() {
        Bundle paperBundle = getIntent().getBundleExtra(INITIAL_PAPERS);
        int paperCount = (int) paperBundle.get(COUNT);
        mGridView.invalidate();
        List<String> paperTitles = new ArrayList<>();
        for (int i = 0; i < paperCount; i++) {
            String title = (String) paperBundle.get(String.valueOf(i));
            paperTitles.add(title);
            Qualifier newQualifier = new Subscription("Title", title, colorArray[i]);
            mQualifiers.add(newQualifier);
            mQualifierConverter.addQualiferToDatabase(newQualifier);
        }
        ((BaseAdapter) mGridView.getAdapter()).notifyDataSetChanged();

        PaperProvider.getInitalPapers(paperTitles)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPaperController::updateSaved,
                        throwable -> System.out.println(throwable.toString())
                        , () -> System.out.println("Successful")

                );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    @Override
    public void onFinishedButtonClicked(String type, String searchField, String searchTerm,
                                        String description, int color) {
        mGridView.invalidate();


        final Qualifier[] newQualifier = new Qualifier[1];
        newQualifier[0] = new Subscription(searchField, searchTerm,
                color);
        if (description != null)
            newQualifier[0].setDescription(description);
        mQualifiers.add(newQualifier[0]);
        mQualifierAdapter.notifyDataSetChanged();

        PaperProvider.getRoot(searchField, searchTerm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(xmlRoot -> {

                            mQualifierConverter.addQualiferToDatabase(newQualifier[0]);


                            test(xmlRoot);

                        },
                        throwable -> System.out.println(throwable.toString())
                        , () -> System.out.println("Successful")

                );


    }

    private void test(XMLRoot xmlRoot) {
        mQualifiers = mQualifierConverter.getQualifiersFromDatabase();

        Qualifier newQualifier = mQualifiers.get(mQualifiers.size() - 1);
        mQualifiers.add(newQualifier);
        ((BaseAdapter) mGridView.getAdapter()).notifyDataSetChanged();
        mPaperController.updateSubscribed(xmlRoot, newQualifier);


    }


}
