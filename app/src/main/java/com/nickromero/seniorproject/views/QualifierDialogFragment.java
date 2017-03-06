package com.nickromero.seniorproject.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.nickromero.seniorproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nickromero on 2/9/17.
 */

public class QualifierDialogFragment extends DialogFragment {

    private static QualifierDialogInterface mDialogInterafce;
    int mNum;
    private int[] mColorNames;
    private Spinner mCategorySpinner;
    private RadioButton mSubscriptionButton;
    private RadioButton mFilterButton;
    private EditText mSearchBox;
    private Spinner mColorSpinner;
    private String sSearchBoxText;

    private View mDialogView;
    private EditText mDescriptionBox;

    /**
     * Represents what qualifier was selected
     */
    private final String SUBSCRIPTION = "Subscription";

    /**
     * Represents what qualifier was selected
     */
    private final String FILTER = "Filter";

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static QualifierDialogFragment newInstance(QualifierDialogInterface dialogInterface) {
        QualifierDialogFragment f = new QualifierDialogFragment();
        mDialogInterafce = dialogInterface;

        // Supply num input as an argument.
        //Bundle args = new Bundle();
        //args.putInt("num", num);
        //f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedArray typedArrayColors = getResources().obtainTypedArray(R.array.colors);
        mColorNames = new int[typedArrayColors.length()];

        for (int i = 0; i < typedArrayColors.length(); i++)
            mColorNames[i] = typedArrayColors.getColor(i, 0);
        typedArrayColors.recycle();

        mColorNames = getResources().getIntArray(R.array.colors);



    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView= inflater.inflate(R.layout.activity_create_qualifier, null);


        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                validateFields();
               prepareCreatedSubscriptionOrFilterBundle();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        mCategorySpinner = (Spinner) mDialogView.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filter_subscriptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Create each of the Radio buttons and assign appropriate listeners
        mSubscriptionButton = (RadioButton) mDialogView.findViewById(R.id.subscriptionRadioButton);
        mFilterButton = (RadioButton) mDialogView.findViewById(R.id.filterRadioButton);
        mSubscriptionButton.setChecked(false);
        mFilterButton.setChecked(false);
        mSubscriptionButton.setOnClickListener(radioButtonClickListener(0));
        mFilterButton.setOnClickListener(radioButtonClickListener(1));

        mSearchBox = (EditText) mDialogView.findViewById(R.id.searchBox);


        mCategorySpinner.setAdapter(adapter);

        mColorSpinner = (Spinner) mDialogView.findViewById(R.id.colorChooser);


        mColorSpinner.setAdapter(new SpinnerAdapter(getActivity().getApplicationContext(),
                R.layout.color_chooser_layout, getResources().getStringArray(R.array.colors)));
        builder.setView(mDialogView);
        return builder.create();

    }


    /**
     * Inner Custom Spinner Class used to populate Circluar Color Views in spinner
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

            LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();

            View row = inflater.inflate(R.layout.color_chooser_layout, parent, false);

            CircleImageView circle = (CircleImageView) row.findViewById(R.id.colorCircle);


            circle.setImageDrawable(new ColorDrawable(mColorNames[position]));


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
                } else {
                    mSubscriptionButton.setChecked(false);
                    mFilterButton.setChecked(true);
                }

            }
        };
    }

    /**
     * Helper method to ensure a user filled in fields correctly
     *
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

        mDescriptionBox = (EditText) mDialogView.findViewById(R.id.customDescription);
        customDescription = mDescriptionBox.getText().toString();

        if (mSubscriptionButton.isChecked()) {
            typeOfModel = SUBSCRIPTION;
        } else {
            typeOfModel = FILTER;
        }


        resultSubOrFilter.putExtra("type", typeOfModel);

        resultSubOrFilter.putExtra("search_term", sSearchBoxText);
        resultSubOrFilter.putExtra("search_field", mCategorySpinner.getSelectedItem().toString());
        resultSubOrFilter.putExtra("color", mColorNames[mColorSpinner.getSelectedItemPosition()]);


        resultSubOrFilter.putExtra("custom_description", customDescription);
        mDialogInterafce.onFinishedButtonClicked(typeOfModel,
                mCategorySpinner.getSelectedItem().toString(),
                sSearchBoxText,
                customDescription,
                mColorNames[mColorSpinner.getSelectedItemPosition()]
                );

    }
}
