package com.nickromero.seniorproject.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nickromero.seniorproject.R;

import java.util.ArrayList;

import data.models.Paper;

/**
 * Created by nickromero on 2/5/17.
 */

public class SuggestedPaperAdapter extends RecyclerView.Adapter<SuggestedPaperAdapter.PaperHolder> {

    private ArrayList<Paper> mPapers;

    private int mExpandedPosition = -1;

    private Fragment mParentFragment;

    private RecyclerView re;

    /**
     * @param papers
     */
    public SuggestedPaperAdapter(ArrayList<Paper> papers, Fragment parentFragment) {

        mPapers = papers;
        mParentFragment = parentFragment;


    }

    @Override
    public PaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggested_paper_layout, parent, false);


        return new PaperHolder(parent.getContext(), inflatedView);
    }


    @Override
    public void onBindViewHolder(PaperHolder holder, int position) {
        Paper paper = mPapers.get(position);

        final boolean isExpanded = position == mExpandedPosition;
        holder.expandArea.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                //TransitionManager.beginDelayedTransition(this);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mParentFragment.getContext()).
                        title("Navigate to Site\n" + paper.getURL())
                        .content("Here you can save this paper to your device" +
                                "for off-line viewing.")
                        .positiveText("Go to URL")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                               /* WebView mWebView = (WebView) LayoutInflater.from(mParentFragment.getContext())
                                        .inflate(R.layout.paper_webview, null);
                                mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);





                                mWebView.loadUrl(paper.getURL());*/
                            }
                        })
                        .negativeText("Close")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });


        holder.bindPaper(paper, position);
    }



    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    public void addItem(Paper paper) {
        mPapers.add(paper);
        notifyDataSetChanged();
    }

    public class PaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView mPapersAuthors;
        private final TextView mPaperTitle;
        private final TextView mPaperAbstract;
        private final TextView mPaperYear;
        private final TextView mPaperPublisher;
        private final TextView mPaperISSN;
        //private final TextView

        private final Context mContext;
        private Paper mPaper;
        private int mPapersPosition;

        private RelativeLayout expandArea;

        public PaperHolder(Context context, View itemView) {
            super(itemView);

            mPapersAuthors = (TextView) itemView.findViewById(R.id.suggested_paper_authors);
            mPaperTitle = (TextView) itemView.findViewById(R.id.suggested_paper_title);
            mPaperAbstract = (TextView) itemView.findViewById(R.id.suggested_paper_abstract);
            expandArea = (RelativeLayout) itemView.findViewById(R.id.suggested_paper_info);
            mPaperYear = (TextView) itemView.findViewById(R.id.year);
            mPaperPublisher = (TextView) itemView.findViewById(R.id.publisher);
            mPaperISSN = (TextView) itemView.findViewById(R.id.issn);

            mContext = context;

        }

        public void bindPaper(Paper paper, int position) {
            mPaper = paper;
            mPapersPosition = position;
            mPapersAuthors.setText("");
            mPaperAbstract.setText(paper.getAbstract());
            mPaperYear.setText(paper.getPubYear());
            mPaperPublisher.setText(paper.getPublisher());
            mPaperISSN.setText(paper.getISSN());

            //mPapersAuthors.setText(mPaper.getAuthors());

            mPaperTitle.setText(mPaper.getTitle());

        }

        @Override
        public void onClick(View v) {

        }
    }
}
