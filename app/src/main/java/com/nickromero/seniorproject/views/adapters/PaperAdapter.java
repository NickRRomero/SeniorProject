package com.nickromero.seniorproject.views.adapters;

import android.support.v4.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.PaperDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import data.models.Paper;
import data.models.Qualifier;
import de.hdodenhof.circleimageview.CircleImageView;
import utilities.PDFViewer;

/**
 * Created by nickromero on 1/3/17.
 */

/**
 * A paper adapter is used to hold the paper cards that are displayed on the Saved/Subscribed Fragment
 */
public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperHolder> {

    /**
     * Container for the paper models
     */
    private ArrayList<Paper> mPapers;

    /**
     * Int representation on which card we should inflate when clicked
     */
    private int mCardViewToInflate;

    /**
     * Parent fragment who is using this adapter
     */
    private Fragment mParentFragment;

    /**
     * Constructor for this adapter
     * @param papers set of papers being displayed in this adapter
     * @param cardViewToInflate which card view we will show to the user
     * @param parentFragment fragment using this adapter
     */
    public PaperAdapter(ArrayList<Paper> papers, int cardViewToInflate, Fragment parentFragment) {
        mPapers = papers;
        mCardViewToInflate = cardViewToInflate;
        mParentFragment = parentFragment;
    }

    /**
     * Extended method to create a new holder
     * @param parent
     * @param viewType
     * @return PaperHolder
     */
    @Override
    public PaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paper_card, parent, false);

        return new PaperHolder(parent.getContext(), inflatedView);
    }

    /**
     * Extended method to bind paper to holder
     * @param holder PaperHolder to bind to
     * @param position of the paper in the adapter
     */
    @Override
    public void onBindViewHolder(PaperHolder holder, int position) {
        Paper paper = mPapers.get(position);
        holder.bindPaper(paper, position);
    }

    /**
     * Remove a paper from the adapter
     * @param position of the paper
     */
    public void removeItem(int position) {
        mPapers.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Add new paper to the adapter
     * @param paper
     */
    public void addItem(Paper paper) {
        if (!mPapers.contains(paper)) {
            mPapers.add(paper);
            notifyItemInserted(mPapers.size() - 1);
            notifyDataSetChanged();
        }
    }

    /**
     * Add a group of papers to the adapter
     * @param papers list of paper objects
     */
    public void addItems(List<Paper> papers) {
        for (Paper paper : papers)
            addItem(paper);
        notifyDataSetChanged();
    }

    /**
     * Clear the adapter of items
     */
    public void removeAllItems() {
        mPapers.clear();
        notifyDataSetChanged();
    }


    /**
     * Get a specific paper from the adapter
     * @param position of the paper
     * @return a paper object
     */
    public Paper getItem(int position) { return mPapers.get(position); }


    /**
     * Get the number of papers in the adapter
     * @return int number of papers
     */
    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    /**
     * Inner class used to represent a Paper Holder object. Paper Holder handles the logic
     * of creating and initializing a paper.
     */
    class PaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * Context associated with the PaperHolder
         */
        private Context mContext;

        /**
         * Paper bound to the holder
         */
        private Paper mPaper;


        /**
         * Title of the paper
         */
        private TextView mPaperTitle;

        /**
         * ViewHolder for a paper's authors
         */
        private LinearLayout mPapersAuthors;

        /**
         * ViewHolder for qualifiers associated with the paper
         */
        private GridLayout mSubscriptionHolder;

        /**
         * Fragment to display when a paper is clicked on
         */
        private DialogFragment mPaperDialogFragment;

        /**
         * Position of a paper in the adapter
         */
        private int mPapersPosition;

        /**
         * Constructor for a Paper Holder. Initializes the appropriate fields for a paper
         * @param context calling context
         * @param itemView
         */
        PaperHolder(android.content.Context context, View itemView) {
            super(itemView);

            mPapersAuthors = (LinearLayout) itemView.findViewById(R.id.authors);
            mPaperTitle = (TextView) itemView.findViewById(R.id.title);
            mSubscriptionHolder = (GridLayout) itemView.findViewById(R.id.colors);

            mSubscriptionHolder.setColumnCount(4);
            mSubscriptionHolder.setRowCount(2);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(view -> {
                if (mParentFragment != null) {
                    mPaperDialogFragment = new PaperDialogFragment().newInstance(mCardViewToInflate, mParentFragment, mPaper,
                            mPapersPosition);

                    mPaperDialogFragment.setTargetFragment(mParentFragment, 6);

                    mPaperDialogFragment.show(mParentFragment.getFragmentManager(), "dialog");
                    return true;
                }
                return true;
            });

            mContext = context;
        }

        /**
         * Bind a paper to this paper holder
         * @param paper object to be bound
         * @param position in the adapter
         */
        void bindPaper(Paper paper, int position) {
            mPaper = paper;
            mPapersPosition = position;

            mSubscriptionHolder.invalidate();
            mSubscriptionHolder.removeAllViews();
            int idarr[] = {R.id.author1, R.id.author2, R.id.author3};
            int i;

            for (i = 0; i < mPaper.getAuthors().size() && i < 3; i++) {
                if (mPapersAuthors.findViewById(idarr[i]) != null) {
                    ((TextView) mPapersAuthors.findViewById(idarr[i]))
                            .setText(mPaper.getAuthors().get(i));
                }
            }
            while (i < 3) {
                mPapersAuthors.removeView(
                        (mPapersAuthors).findViewById(idarr[i]));
                i++;
            }

            mPaperTitle.setText(paper.getTitle());
            mPaperTitle.setTag(mPaper.getFileName());
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ArrayList<Qualifier> qualifiers = mPaper.getQualifiers();
            if (qualifiers.size() > 0) {
                for (Qualifier qualifier : qualifiers) {
                    LinearLayout circleHolder = (LinearLayout)
                            inflater.inflate(R.layout.circle_image, null);

                    ((CircleImageView) circleHolder.findViewById(R.id.circle_qualifier))
                            .setImageDrawable(new ColorDrawable(qualifier.getColor()));
                    mSubscriptionHolder.addView(circleHolder);
                }
            }
        }

        /**
         * Start an intent to display a pdf in a pdf reader
         * @param view ViewHolder containing the pdf that was clicked on
         */
        @Override
        public void onClick(View view) {

            if (view.findViewById(R.id.md_title) != null) {
                String paperTitle = (String) view.findViewById(R.id.md_title).getTag();
                PDFViewer viewer = new PDFViewer();
                viewer.openPDF("", paperTitle, view);

            }
            else
            {
                CharSequence paperMissingText = "File not found.";
                Toast toast = Toast.makeText(mContext, paperMissingText, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
