package com.nickromero.seniorproject.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nickromero.seniorproject.R;

/**
 * Created by nickromero on 1/9/17.
 */
public class SubscriptionFilterActivity extends AppCompatActivity{

    private Spinner mSpinner;

    private Spinner mColorSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_subscription_or_filter);

        Intent intent = getIntent();

        mSpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_subscriptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);



        mColorSpinner = (Spinner) findViewById(R.id.colorChooser);

    }
}
