package com.peryite.journeyd3.presenters;

import com.peryite.journeyd3.contracts.ChapterContract;

public class ChapterFragmentPresenter implements ChapterContract.Presenter {

    private ChapterContract.View view;

    public ChapterFragmentPresenter(ChapterContract.View view) {
        this.view = view;
    }

    @Override
    public void resetChapter() {
        view.reset();
    }

    @Override
    public void updateChapter() {
        view.update();
    }
}
