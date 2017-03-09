package com.nickromero.seniorproject.views.fragments;

import com.nickromero.seniorproject.views.BasePresenter;
import com.nickromero.seniorproject.views.BaseView;

import java.util.List;

import data.models.Paper;

/**
 * Created by nickromero on 2/18/17.
 *
 * A PaperContract defines the interfaces and methods for which a paper Presenter/View must follow.
 */
public class PaperContract {

    /**
     * Template for a view
     */
    interface View extends BaseView<Presenter> {
        void updatePapers(List<Paper> papers);
    }

    /**
     * Template for a presenter
     */
    interface Presenter extends BasePresenter{
        void loadPapers(String author);
    }
}
