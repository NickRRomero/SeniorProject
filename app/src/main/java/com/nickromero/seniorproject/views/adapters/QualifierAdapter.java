package com.nickromero.seniorproject.views.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.nickromero.seniorproject.R;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.ArrayList;

import data.SQLConverter.SQLQualifierConverter;
import data.models.Qualifier;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nickromero on 1/19/17.
 */


public class QualifierAdapter extends BaseAdapter {

    /**
     * Context if the application that the adapter will be used in
     */
    private Context mContext;

    /**
     * List of currently build qualifiers
     */
    private ArrayList<Qualifier> mQualifiers;

    /**
     * Circle used to hold a qualifiers color representation
     */
    private CircleImageView mQualifierColor;

    /**
     * IEEE category of a qualifier
     */
    private TextView mCategoryTextView;

    /**
     * Search term of a qualifier
     */
    private TextView mSearchTermTextView;

    /**
     * Optional description of a qualifier
     */
    private TextView mDescriptionTextView;

    /**
     * Array to hold color ids from colors.xml
     */
    private int[] colorIDs;


    public QualifierAdapter(Context c, ArrayList<Qualifier> qualifiers) {
        mContext = c;
        mQualifiers = qualifiers;

        //Grab color values from colors.xml
        colorIDs = mContext.getResources().getIntArray(R.array.colors);
    }

    /**
     * Get the number of total qualifiers created
     * @return
     */
    public int getCount() {
        return mQualifiers.size();
    }

    /**
     * Not used
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    public void removeQualifier(int position) {

        SQLQualifierConverter
                .getInstance(mContext)
                .deleteQualifierFromDatabase(mQualifiers.get(position));



        mQualifiers.remove(position);
        notifyDataSetChanged();
    }

    /**
     * Not used
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Return the custom view of a finished qualifer. These views are inflated in the
     * subscription/filters drawer at the bottom of a screen
     * @param position The index of a view in the layout
     * @param view
     * @param viewGroup parent view group
     * @return newly created qualifer view
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.qualifier_layout,
                viewGroup, false);


        //Get color view
        mQualifierColor = (CircleImageView) inflatedView.findViewById(R.id.builtQualifierColor);

        //Get category view
        mCategoryTextView = (TextView) inflatedView.findViewById(R.id.builtQualifierCategoryText);

        //Get search term view
        mSearchTermTextView = (TextView) inflatedView.findViewById(R.id.builtQualifierSearchTerm);

        //Get description view
        mDescriptionTextView = (TextView) inflatedView.findViewById(R.id.builtQualifierDescription);

        //Assign the values to the appropriate fields in the qualifier card
        mQualifierColor.setImageDrawable(new ColorDrawable(mQualifiers.get(position).getColor()));
        mCategoryTextView.setText(mQualifiers.get(position).getCategory());
        mSearchTermTextView.setText(mQualifiers.get(position).getSearchTerm());
        mDescriptionTextView.setText(mQualifiers.get(position).getDescription());

        inflatedView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title(R.string.qualifier_dialog_title)

                        .positiveText(R.string.delete)
                        .onPositive((dialog, which) -> {
                            removeQualifier(position);
                            dialog.dismiss();
                        })
                        .negativeText(R.string.cancel)
                        .onNegative(((dialog, which) -> {
                            dialog.dismiss();
                        }))
                        .show();
                return false;
            }
        });


        return inflatedView;




    }
}
