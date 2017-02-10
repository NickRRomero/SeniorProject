package com.nickromero.seniorproject.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.nickromero.seniorproject.views.adapters.PaperAdapter;
import com.nickromero.seniorproject.views.adapters.QualifierAdapter;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.SuggestedPaperAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import data.Paper;
import data.Qualifier;
import data.Subscription;
import data.enums.PaperType;

public class MainActivity extends AppCompatActivity implements QualifierDialogInterface{

    private Intent createSubOrFilter;

    private final int CREATE_ACTIVITY_CODE = 1;

    private ArrayList<Qualifier> mQualifiers;

    private GridView mGridView;

    private String[] colorArray;

    private PaperFragment mPaperFragments;

    private static PaperController mPaperController;

    private PaperAdapter test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createSubOrFilter = new Intent(this, CreateQualifierActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorArray = getResources().getStringArray(R.array.colors);


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

        mPaperController = PaperController.getInstance();


        mPaperController.setAdapter( new PaperAdapter(initSavedData(),
                        0, mPaperFragments.mSavedFragment)
                , PaperType.SAVED);
        mPaperController.setAdapter(
                new PaperAdapter(initSubscribedData(),
                        1,
                        mPaperFragments.mSubscribedFragment), PaperType.SUBSCRIBED);

        mPaperController.setAdapter(new SuggestedPaperAdapter(initSuggestedData(), mPaperFragments.mSuggestedFragment));

        mPaperController.setFragment(mPaperFragments.mSavedFragment, PaperType.SAVED);
        mPaperController.setFragment(mPaperFragments.mSubscribedFragment, PaperType.SUBSCRIBED);
        mPaperController.setFragment(mPaperFragments.mSuggestedFragment, PaperType.SUGGESTED);

//        mPaperController.attachAdapterToView(PaperType.SAVED);
  //      mPaperController.attachAdapterToView(PaperType.SUBSCRIBED);

        //Build GridView to hold future created subscriptions

        mQualifiers = new ArrayList<>();
        initData();
        mGridView = (GridView) findViewById(R.id.createdSubscriptions);
        mGridView.setAdapter(new QualifierAdapter(this, mQualifiers));

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




    /**
     * Used for demo purposes to initialize a qualifier list
     */
    public void initData() {

        mQualifiers.add(new Subscription("Abstract", "Security", Color.GREEN));
        mQualifiers.add(new Subscription("Author", "Andrea Zanella", Color.RED));
        mQualifiers.add(new Subscription("Title", "5G Network", Color.YELLOW));
        mQualifiers.add(new Subscription("Title", "Smart", Color.BLACK));
        mQualifiers.add(new Subscription("Abstract", "Neutrinos", Color.BLUE));
        mQualifiers.add(new Subscription("Title", "Supernova", Color.CYAN));
        mQualifiers.add(new Subscription("Title", "Neutrinos", Color.LTGRAY));
        mQualifiers.add(new Subscription("Year", "1999", Color.MAGENTA));
        mQualifiers.add(new Subscription("Author", "Petr Vogel", Color.DKGRAY));

    }

    private ArrayList<Paper> initSavedData() {
        ArrayList<Paper> mPapersList = new ArrayList<>();


        Paper paper5 = new Paper("Survey of 5G Network: Architecture and Emerging Technologies",
                new ArrayList<String>(Arrays.asList("AKHIL GUPTA", "RAKESH KUMAR JHA")), "url", "5g.pdf");
        paper5.addQualifier(new Subscription("Title", "5G Network", Color.YELLOW));

        mPapersList.add(paper5);


        Paper chemPaper = new Paper("Design of End-Effectors " +
                "for a Chemistry Automation Plant",
                new ArrayList<String>(Arrays.asList("Akshaya Kumar", "Kamila Pillearachichige", "Hamid Sharifi",
                        "Ben Shaw", "Frazer K. Noble")), "url", "chemistry.pdf");
        mPapersList.add(chemPaper);
        chemPaper.addQualifier(new Subscription("Year", "1999", Color.MAGENTA));


        Paper gamePaper = new Paper("Does Gamification Work? — A Literature Review of Empirical Studies on Gamification",
                new ArrayList<String>(Arrays.asList("Juho Hamari", "Jonna Koivisto", "Harri Sarsa")), "url", "gamification.pdf");

        mPapersList.add(gamePaper);
        gamePaper.addQualifier(new Subscription("Year", "1999", Color.MAGENTA));

        mPapersList.add(new Paper("The Internet of Things for Health Care: A Comprehensive Survey",
                new ArrayList<String>(Arrays.asList("S. M. RIAZUL ISLAM", "DAEHAN KWAK", "MD. HUMAUN KABIR", "MAHMUD HOSSAIN"
                        , "KYUNG-SUP KWAK")), "url", "healthcare.pdf"));


        Paper andPaper = new Paper("Internet of Things for Smart Cities",
                new ArrayList<String>(Arrays.asList("Andrea Zanella", "Nicola Bui", "Angelo Castellani"
                        , "Lorenzo Vangelista", "Michele Zorzi")), "url", "iot.pdf");

        andPaper.addQualifier(new Subscription("Author", "Andrea Zanella", Color.RED));
        andPaper.addQualifier(new Subscription("Title", "Smart", Color.BLACK));
        mPapersList.add(andPaper);


        Paper securityPaper = new Paper("Information Security in Big Data: Privacy and Data Mining",
                new ArrayList<String>(Arrays.asList("LEI XU", "CHUNXIAO JIANG", "JIAN WANG",
                        "JIAN YUAN", "YONG REN")), "url", "issecurity.pdf");
        securityPaper.addQualifier(new Subscription("Abstract", "Security", Color.GREEN));

        mPapersList.add(securityPaper);


        mPapersList.add(new Paper("Predicting the Future With Social Media",
                new ArrayList<String>(Arrays.asList("Sitaram Asur", "Bernardo A. Huberman")), "url", "mediafuture.pdf"));

        mPapersList.add(new Paper("High-Performance Extreme Learning Machines: A Complete Toolbox for Big Data Applications",
                new ArrayList<String>(Arrays.asList("ANTON AKUSOK", "KAJ-MIKAEL BJÖRK", "YOAN MICHE", "AMAURY LENDASSE")), "url", "performance.pdf"));

        return mPapersList;
    }

    private ArrayList<Paper> initSubscribedData() {
        ArrayList<Paper> mPapersList = new ArrayList<>();

        Paper spacePaper = new Paper("WHAT MIGHT WE LEARN FROM A FUTURE SUPERNOVA NEUTRINO SIGNAL?",
                new ArrayList<String>(Arrays.asList("PETR VOGEL")), "url", "space.pdf");
        mPapersList.add(spacePaper);

        spacePaper.addQualifier(new Subscription("Abstract", "Neutrinos", Color.BLUE));
        spacePaper.addQualifier(new Subscription("Title", "Supernova", Color.CYAN));
        spacePaper.addQualifier(new Subscription("Title", "Neutrinos", Color.LTGRAY));
        spacePaper.addQualifier(new Subscription("Year", "1999", Color.MAGENTA));
        spacePaper.addQualifier(new Subscription("Author", "Petr Vogel", Color.DKGRAY));

        return mPapersList;
    }

    private ArrayList<Paper> initSuggestedData() {
        ArrayList<Paper> mPapersList = new ArrayList<>();
        Paper paper5;

        for (int i = 0; i < 20; i++) {
            paper5 = new Paper("Survey of 5G Network: Architecture and Emerging Technologies",
                    new ArrayList<String>(Arrays.asList("AKHIL GUPTA", "RAKESH KUMAR JHA")), "url", "5g.pdf");
            mPapersList.add(paper5);
        }

        return mPapersList;
    }

    @Override
    public void onFinishedButtonClicked(String type, String searchField, String searchTerm,
                                        String description, int color) {
        mGridView.invalidate();
        Qualifier newQualifier;
        newQualifier = new Subscription(searchField,searchTerm,
                color);
        if (description != null)
            newQualifier.setDescription(description);

        mQualifiers.add(newQualifier);
        ((BaseAdapter) mGridView.getAdapter()).notifyDataSetChanged();
    }
}
