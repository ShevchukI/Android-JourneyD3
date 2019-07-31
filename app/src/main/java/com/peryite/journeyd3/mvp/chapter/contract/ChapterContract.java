package com.peryite.journeyd3.mvp.chapter.contract;

import android.content.DialogInterface;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.mvp.BaseView;

import java.util.List;

public interface ChapterContract {

    interface Presenter {

        void start();

        void restart();

        void update();
    }

    interface View extends BaseView<Presenter> {

        void createAdapterWithChapters(List<Chapter> chapters);

        void showDialog(String message, DialogInterface.OnClickListener okListener);

        void showProgressBar();

        void hideProgressBar();

        void showRecyclerView();

        void hideRecyclerView();
    }
}
