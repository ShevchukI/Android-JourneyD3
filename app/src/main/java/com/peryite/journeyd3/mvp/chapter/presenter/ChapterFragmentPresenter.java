package com.peryite.journeyd3.mvp.chapter.presenter;

import com.peryite.journeyd3.mvp.chapter.contract.ChapterContract;

public class ChapterFragmentPresenter implements ChapterContract.Presenter {

    private ChapterContract.View view;

    public ChapterFragmentPresenter(ChapterContract.View view) {
        this.view = view;
        view.setPresenter(this);
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
