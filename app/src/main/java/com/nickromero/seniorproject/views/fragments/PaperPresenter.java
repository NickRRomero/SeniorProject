package com.nickromero.seniorproject.views.fragments;

import android.support.annotation.NonNull;

import com.nickromero.seniorproject.views.fragments.PaperContract;

import data.providers.PaperProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by nickromero on 2/18/17.
 */

public class PaperPresenter implements PaperContract.Presenter {

    @NonNull
    private final PaperContract.View mSuggestedFragmentView;
    @NonNull
    private CompositeDisposable mDisposables;


    public PaperPresenter(@NonNull PaperContract.View suggestedView) {
        mSuggestedFragmentView = suggestedView;
        mDisposables = new CompositeDisposable();
        mSuggestedFragmentView.setPresenter(this);
    }

    @Override
    public void loadPapers(String author) {
        mDisposables.clear();
        Disposable disposable = PaperProvider.getAuthorPapers(author)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mSuggestedFragmentView::updatePapers,
                        Timber::e,
                        () -> Timber.i("getting Papers completed")
                );
        mDisposables.add(disposable);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mDisposables.clear();
    }
}
