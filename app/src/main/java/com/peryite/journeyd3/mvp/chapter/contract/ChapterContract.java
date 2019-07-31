package com.peryite.journeyd3.mvp.chapter.contract;

import android.content.DialogInterface;
import android.support.design.chip.ChipGroup;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.mvp.BaseView;
import com.peryite.journeyd3.services.ChapterService;

import java.util.List;

public interface ChapterContract {

    interface Presenter {

        void start();

        void restart();

        void update();
    }

    interface View  extends BaseView<Presenter> {

        void createAdapterWithChapters(List<Chapter> chapters);

        void showDialog(String message, DialogInterface.OnClickListener okListener);

        void restart();

        void reset();

        void update();

        void showProgressBar();
        void hideProgressBar();

        void showRecyclerView();

        void hideRecyclerView();
    }
}
