package com.nickromero.seniorproject.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.nickromero.seniorproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nickromero on 1/9/17.
 */
public class SubscriptionFilterActivity extends AppCompatActivity {

    private Spinner mSpinner;

    private Spinner mColorSpinner;

    private RadioButton mSubscriptionButton;

    private RadioButton mFilterButton;

    private EditText mSearchBox;

    private String sSearchBoxText;

    private final String SUBSCRIPTION = "Subscription";

    private final String FILTER = "Filter";

    private EditText mDescriptionBox;

    private final String[] colorNames = {"red", "pink", "purple", "deepPurple",
            "indigo", "blue", "lightBlue", "cyan", "teal", "green", "lightGreen", "lime",
            "yellow", "amber", "orange", "deepOrange", "brown", "grey", "blueGrey", "black"};

    private FloatingActionButton finishedFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_create_subscription_or_filter);

        Intent intent = getIntent();



        mSpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_subscriptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Create each of the Radio buttons and assign approrpriate listeners
        mSubscriptionButton = (RadioButton) findViewById(R.id.subscriptionRadioButton);
        mFilterButton = (RadioButton) findViewById(R.id.filterRadioButton);
        mSubscriptionButton.setChecked(false);
        mFilterButton.setChecked(false);
        mSubscriptionButton.setOnClickListener(radioButtonClickListener(0));
        mFilterButton.setOnClickListener(radioButtonClickListener(1));

        mSearchBox = (EditText) findViewById(R.id.searchBox);


        mSpinner.setAdapter(adapter);

        mColorSpinner = (Spinner) findViewById(R.id.colorChooser);

        mColorSpinner.setAdapter(new SpinnerAdapter(getApplicationContext(),
                R.layout.spinner_color_chooser, colorNames));

        finishedFab = (FloatingActionButton) findViewById(R.id.finishedCreateSection);
        finishedFab.setOnClickListener(createFabOnClickListener());
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

    /**
     * Handles creating an on click listeer fo reach of the radio buttons in this activity.
     *
     * @param whichButton 0 == Subscription. 1 == Filter
     * @return
     */
    private View.OnClickListener radioButtonClickListener(final int whichButton) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Subscription Radio Button
                if (whichButton == 0) {
                    mFilterButton.setChecked(false);
                    mSubscriptionButton.setChecked(true);
                }
                else {
                    mSubscriptionButton.setChecked(false);
                    mFilterButton.setChecked(true);
                }

            }
        };
    }

    private View.OnClickListener createFabOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean successfulEntry = validateFields();

                if (successfulEntry) {
                    prepareCreatedSubscriptionOrFilterBundle();
                }
                else {
                    Snackbar.make(view, "Please fill in all required fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        };
    }

    /**
     * Helper method to ensure a user filled in fields correctly
     * @return
     */
    private boolean validateFields() {
        boolean radioButtonsChecked;
        boolean searchTermEntered = true;


        radioButtonsChecked = mFilterButton.isChecked() || mSubscriptionButton.isChecked();
        sSearchBoxText = mSearchBox.getText().toString().trim();
        if (sSearchBoxText.isEmpty() || sSearchBoxText.length() == 0
                || sSearchBoxText.equals("") || sSearchBoxText == null)
            searchTermEntered = false;


        return radioButtonsChecked && searchTermEntered;
    }

    private void prepareCreatedSubscriptionOrFilterBundle() {
        Intent resultSubOrFilter = new Intent();
        String typeOfModel;
        String customDescription;

        mDescriptionBox = (EditText) findViewById(R.id.customDescription);
        customDescription = mDescriptionBox.getText().toString();

        if (mSubscriptionButton.isChecked()) {
            typeOfModel = SUBSCRIPTION;
        }
        else {
            typeOfModel = FILTER;
        }



        resultSubOrFilter.putExtra("type", typeOfModel);

        resultSubOrFilter.putExtra("search_term", sSearchBoxText);
        resultSubOrFilter.putExtra("search_field", mSpinner.getSelectedItem().toString());
        resultSubOrFilter.putExtra("color", colorNames[mColorSpinner.getSelectedItemPosition()]);

        if (customDescription.isEmpty() || customDescription.length() == 0
                || customDescription.equals("") || customDescription == null)
            resultSubOrFilter.putExtra("custom_description", customDescription);

        setResult(Activity.RESULT_OK, resultSubOrFilter);


        finish();
    }
}
