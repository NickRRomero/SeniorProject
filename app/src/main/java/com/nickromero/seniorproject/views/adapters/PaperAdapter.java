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

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperHolder> {

    private ArrayList<Paper> mPapers;


    private int mCardViewToInflate;

    private Fragment mParentFragment;


    /**
     * @param papers
     */
    public PaperAdapter(ArrayList<Paper> papers, int cardViewToInflate, Fragment parentFragment) {
        mPapers = papers;

        mCardViewToInflate = cardViewToInflate;
        mParentFragment = parentFragment;
    }

    @Override
    public PaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paper_card, parent, false);

        return new PaperHolder(parent.getContext(), inflatedView);
    }

    @Override
    public void onBindViewHolder(PaperHolder holder, int position) {
        Paper paper = mPapers.get(position);


        holder.bindPaper(paper, position);
    }

    public void removeItem(int position) {
        mPapers.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Paper paper) {
        if (!mPapers.contains(paper)) {
            System.out.println("HERE");
            mPapers.add(paper);
            notifyItemInserted(mPapers.size() - 1);
            notifyDataSetChanged();
        }
    }

    public void addItems(List<Paper> papers) {
        for (Paper paper : papers)
            addItem(paper);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        mPapers.clear();
        notifyDataSetChanged();

    }



    public Paper getItem(int position) {

        return mPapers.get(position);
    }



    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    public class PaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;

        private Paper mPaper;

        private int[] mColorNames;


        //Should hold the title of a paper
        private TextView mPaperTitle;

        //Should hold the author(s) of a paper
        private LinearLayout mPapersAuthors;

        private GridLayout mSubscriptionHolder;

        private static final String PAPER_KEY = "PAPER";

        private DialogFragment mPaperDialogFragment;

        private int mPapersPosition;


        public PaperHolder(android.content.Context context, View itemView) {
            super(itemView);


            mPapersAuthors = (LinearLayout) itemView.findViewById(R.id.authors);
            mPaperTitle = (TextView) itemView.findViewById(R.id.title);


            mSubscriptionHolder = (GridLayout) itemView.findViewById(R.id.colors);


            mSubscriptionHolder.setColumnCount(4);
            mSubscriptionHolder.setRowCount(2);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mParentFragment != null) {
                        mPaperDialogFragment = new PaperDialogFragment().newInstance(mCardViewToInflate, mParentFragment, mPaper,
                                mPapersPosition);


                        mPaperDialogFragment.setTargetFragment(mParentFragment, 6);

                        mPaperDialogFragment.show(mParentFragment.getFragmentManager(), "dialog");
                        return true;
                    }
                    return true;
                }
            });


            mContext = context;


        }

        public void bindPaper(Paper paper, int position) {
            mPaper = paper;
            mPapersPosition = position;

            mSubscriptionHolder.invalidate();
            mSubscriptionHolder.removeAllViews();
            int idarr[] = {R.id.author1, R.id.author2, R.id.author3};
            int i = 0;

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
                    //qualifierCircle.setImageDrawable(new ColorDrawable(qualifier.getColor()));
                    ((CircleImageView) circleHolder.findViewById(R.id.circle_qualifier))
                            .setImageDrawable(new ColorDrawable(qualifier.getColor()));
                    mSubscriptionHolder.addView(circleHolder);
                }
            }


        }

        @Override
        public void onClick(View view) {

            PDFViewer viewer = new PDFViewer();
            viewer.openPDF("", (String) view.findViewById(R.id.title).getTag(), view);
        }


    }


}
