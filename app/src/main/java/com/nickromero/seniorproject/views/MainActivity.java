package com.nickromero.seniorproject.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import com.nickromero.seniorproject.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Intent createSubOrFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createSubOrFilter = new Intent(this, SubscriptionFilterActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        int i = 0;

//        final ImageView icon = (ImageView) findViewById(R.id.expandIcon);

        SlidingUpPanelLayout panel = (SlidingUpPanelLayout) findViewById(R.id.slidingPanel);

        final Animation rotate = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        final LinearLayout main = (LinearLayout) findViewById(R.id.saved_fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                startActivity(createSubOrFilter);




            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PaperFragment(getSupportFragmentManager(), MainActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


/*
        panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon.startAnimation(rotate);

            }
        });*/

        /*
        fina
        l Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        final Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

       /* final RelativeLayout hiddenPanel = (RelativeLayout) findViewById(R.id.subscriptionDrawer);

        hiddenPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenPanel.getVisibility() == View.VISIBLE) {
                    hiddenPanel.startAnimation(bottomDown);
                    //hiddenPanel.setVisibility(View.INVISIBLE);
                }
                else {
                    hiddenPanel.setVisibility(View.VISIBLE);
                    hiddenPanel.startAnimation(bottomUp);

                }
            }
        });*/

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
}
