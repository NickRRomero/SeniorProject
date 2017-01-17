package com.nickromero.seniorproject.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.media.Image;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickromero.seniorproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import data.Paper;
import utilities.PDFViewer;

/**
 * Created by nickromero on 1/3/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PaperHolder> {

    private ArrayList<Paper> mPapers;


    public RecyclerAdapter(ArrayList<Paper> papers) {
        mPapers = papers;
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

        //This should be for the first page display of the paper
        private ImageView mPaperImage;

        //Should hold the title of a paper
        private TextView mPaperTitle;

        //Should hold the author(s) of a paper
        private TextView mPapersAuthors;

        private GridLayout mSubscriptionHolder;

        private static final String PAPER_KEY = "PAPER";

        public PaperHolder(android.content.Context context, View itemView) {
            super(itemView);

            mPaperImage = (ImageView) itemView.findViewById(R.id.image);

            mPapersAuthors = (TextView) itemView.findViewById(R.id.info);
            mPaperTitle = (TextView) itemView.findViewById(R.id.author);

            mSubscriptionHolder = (GridLayout) itemView.findViewById(R.id.colors);
            itemView.setOnClickListener(this);

            mContext = context;


        }

        public void bindPaper(Paper paper) {
            mPaper = paper;

            mPapersAuthors.setText(paper.getAuthors().toString());
            mPaperTitle.setText(paper.getTitle());
            mPaperImage.setImageBitmap(paper.getFirstPageBitMap());
        }

        @Override
        public void onClick(View view) {



            PDFViewer viewer = new PDFViewer();
            viewer.openPDF("", "", view);
            //viewer.displayFirstPage(view, "");

        }
    }


}
