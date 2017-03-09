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

import org.w3c.dom.Text;

/**
 * IntroActivity handles initial users to the app. It is used to build an initial collection of papers
 * for the user. Ideally this class should only be seen on the initial download/use.
 */
public class IntroActivity extends AppCompatActivity {

    /**
     * Current field being filled out by the user
     */
    private int mCurrentPaperEntryLevel = 0;

    /**
     * Super class call
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

            inflateNextPaperEntryLayout();
    }

    /**
     * Dynamically create another entry for the user. Each time an entry's fab is clicked
     * we create another field
     */
    private void inflateNextPaperEntryLayout() {
        mCurrentPaperEntryLevel++;
        final LayoutInflater inflater = getLayoutInflater();
        LinearLayout parent  = (LinearLayout) findViewById(R.id.paperEntryLayoutHolder);
        final View theView = inflater.inflate(R.layout.intro_paper_layout, parent, false);
        final EditText paperEntryText = (EditText) theView.findViewById(R.id.paperEntryEditText);

        //Change the background color for a user
        paperEntryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = paperEntryText.getText().length();
                if(len == 1) {
                    paperEntryText.setBackgroundColor(Color.WHITE);
                }
                else if (len < 1) {
                    paperEntryText.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //Handle creating a new view
        theView.findViewById(R.id.nextPaperFab).setOnClickListener(view -> {

            RelativeLayout parentView = (RelativeLayout) view.getParent();
            parentView.removeView(view);
            parentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            //Ensure the user has entered at least 3 papers
            if (mCurrentPaperEntryLevel == 3) {
               View v = inflater.inflate(R.layout.intro_fab, ((RelativeLayout) findViewById(R.id.introLayout)), true);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent newUserIntent = new Intent(getApplicationContext(), MainActivity.class);
                        int childCount = parent.getChildCount();
                        Bundle bundle = new Bundle();
                        int filledCount = 0;

                        //Get each of the entered papers and save their titles to be later querued against
                        //the ieee gateway
                        for (int i = 0; i < childCount; i++) {
                            RelativeLayout relativeLayout = (RelativeLayout) parent.getChildAt(i);
                            EditText editText = (EditText) relativeLayout.getChildAt(0);
                            String title = editText.getText().toString();

                            if (!title.isEmpty()) {
                                filledCount++;
                                bundle.putSerializable(String.valueOf(i), editText.getText().toString());
                            }

                        }
                        bundle.putSerializable("count", filledCount);
                        newUserIntent.putExtra("initialPapers", bundle);
                        startActivity(newUserIntent);
                    }
                });
            }
            inflateNextPaperEntryLayout();
        });

        parent.addView(theView);
    }
}
