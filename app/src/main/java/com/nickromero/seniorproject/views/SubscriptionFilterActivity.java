package com.nickromero.seniorproject.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nickromero.seniorproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nickromero on 1/9/17.
 */
public class SubscriptionFilterActivity extends AppCompatActivity {

    private Spinner mSpinner;

    private Spinner mColorSpinner;

    private final String[] colorNames = {"red", "pink", "purple", "deepPurple",
            "indigo", "blue", "lightBlue", "cyan", "teal", "green", "lightGreen", "lime",
            "yellow", "amber", "orange", "deepOrange", "brown", "grey", "blueGrey", "black"};

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

        mColorSpinner.setAdapter(new SpinnerAdapter(getApplicationContext(),
                R.layout.spinner_color_chooser, colorNames));


    }


    /**
     * Inner Custom Spinner Class used to populate Circluar Color Views in spinner
     *
     */
    public class SpinnerAdapter extends ArrayAdapter {

        public SpinnerAdapter(Context context, int resource, String[] colors) {
            super(context, resource, colors);
        }

        @Override
        public CircleImageView getView(int position, View convertView, ViewGroup parent) {

            return getDropDownView(position, convertView, parent);
        }

        @Override
        public CircleImageView getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getLayoutInflater();

            View row = inflater.inflate(R.layout.spinner_color_chooser, parent, false);

            CircleImageView circle = (CircleImageView) row.findViewById(R.id.colorCircle);


            int resID = getResources().getIdentifier(colorNames[position], "color", getPackageName());

            circle.setImageResource(resID);


            return circle;


        }
    }
}
