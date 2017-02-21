package com.nickromero.seniorproject.views.intro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Visibility;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.MainActivity;

public class IntroActivity extends AppCompatActivity {

    private int mCurrentPaperEntryLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



            inflateNextPaperEntryLayout();



    }

    private void inflateNextPaperEntryLayout() {
        mCurrentPaperEntryLevel++;
        final LayoutInflater inflater = getLayoutInflater();
        LinearLayout parent  = (LinearLayout) findViewById(R.id.paperEntryLayoutHolder);
        final View theView = inflater.inflate(R.layout.intro_paper_layout, parent, false);
        final EditText paperEntryText = (EditText) theView.findViewById(R.id.paperEntryEditText);
        paperEntryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = paperEntryText.getText().length();
                if(len == 1) {
                    paperEntryText.setBackgroundColor(Color.WHITE);
                    //paperEntryText.setSelection(search.getText().length());

                }
                else if (len < 1) {
                    paperEntryText.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        theView.findViewById(R.id.nextPaperFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theView.getLayoutParams();

                RelativeLayout parentView = (RelativeLayout) view.getParent();
                parentView.removeView(view);
                parentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                if (mCurrentPaperEntryLevel == 3) {
                   View v = inflater.inflate(R.layout.intro_fab, ((RelativeLayout) findViewById(R.id.introLayout)), true);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent newUserIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(newUserIntent);
                        }
                    });
                }
                inflateNextPaperEntryLayout();
            }
        });

        parent.addView(theView);



    }
}
