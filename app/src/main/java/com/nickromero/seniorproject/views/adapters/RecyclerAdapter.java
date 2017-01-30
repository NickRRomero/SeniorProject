package com.nickromero.seniorproject.views.adapters;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.PaperDialogFragment;

import java.util.ArrayList;

import data.Paper;
import data.Qualifier;
import de.hdodenhof.circleimageview.CircleImageView;
import utilities.PDFViewer;

/**
 * Created by nickromero on 1/3/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PaperHolder> {

    private ArrayList<Paper> mPapers;


    private final int mCardViewToInflate;

    private static Fragment mParentFragment;

    /**
     *
     * @param papers
     */
    public RecyclerAdapter(ArrayList<Paper> papers, int cardViewToInflate, Fragment parentFragment) {
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

        holder.bindPaper(paper);
    }



    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    public static class PaperHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;

        private Paper mPaper;

        private int [] mColorNames;



        //Should hold the title of a paper
        private TextView mPaperTitle;

        //Should hold the author(s) of a paper
        private LinearLayout mPapersAuthors;

        private GridLayout mSubscriptionHolder;

        private static final String PAPER_KEY = "PAPER";

        private DialogFragment mPaperDialogFragment;

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
                    mPaperDialogFragment = new PaperDialogFragment().newInstance(0, mParentFragment, mPaper);
                    mPaperDialogFragment.show(mParentFragment.getActivity().getFragmentManager(), "dialog");
                    return true;
                }
            });


            mContext = context;


        }

        public void bindPaper(Paper paper) {
            mPaper = paper;


            int idarr[] = {R.id.author1, R.id.author2, R.id.author3};

            for (int i = 0; i < mPaper.getAuthors().size() && i < 3; i++) {
                ((TextView )mPapersAuthors.findViewById(idarr[i]))
                        .setText(mPaper.getAuthors().get(i));
            }




            mPaperTitle.setText(paper.getTitle());
            mPaperTitle.setTag(mPaper.getFileName().toString());

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
