package com.nickromero.seniorproject.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.nickromero.seniorproject.views.adapters.QualifierAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import com.nickromero.seniorproject.R;

import java.util.ArrayList;

import data.Qualifier;
import data.Subscription;

public class MainActivity extends AppCompatActivity {

    private Intent createSubOrFilter;

    private final int CREATE_ACTIVITY_CODE = 1;

    private ArrayList<Qualifier> mQualifiers;

    private GridView mGridView;

    private String[] colorArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createSubOrFilter = new Intent(this, CreateQualifierActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorArray = getResources().getStringArray(R.array.colors);


        int i = 0;


        SlidingUpPanelLayout panel = (SlidingUpPanelLayout) findViewById(R.id.slidingPanel);

        final Animation rotate = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        final LinearLayout main = (LinearLayout) findViewById(R.id.saved_fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                startActivityForResult(createSubOrFilter, CREATE_ACTIVITY_CODE);




            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PaperFragment(getSupportFragmentManager(), MainActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


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
     * Handles any activities returning to any of the fragments
     * @param requestCode int code used to determine which activity is returning to the main activity
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (CREATE_ACTIVITY_CODE): {
                if (resultCode == Activity.RESULT_OK) {
                    mGridView.invalidate();
                    Qualifier newQualifer;
                    newQualifer = new Subscription(data.getStringExtra("search_field"),
                                                   data.getStringExtra("search_term"),
                                                   data.getIntExtra("color", 0));
                    if (data.getStringExtra("custom_description") != null)
                        newQualifer.setDescription(data.getStringExtra("custom_description"));

                    mQualifiers.add(newQualifer);
                    ((BaseAdapter) mGridView.getAdapter()).notifyDataSetChanged();

                }
            }
        }
    }

    /**
     * Used for demo purposes to initialize a qualifier list
     */
    public void initData() {

        mQualifiers.add(new Subscription("Abstract", "Security", Color.GREEN));
        mQualifiers.add(new Subscription("Author", "Andrea Zanella", Color.RED));
        mQualifiers.add(new Subscription("Title", "5G Network", Color.YELLOW));

    }

}
