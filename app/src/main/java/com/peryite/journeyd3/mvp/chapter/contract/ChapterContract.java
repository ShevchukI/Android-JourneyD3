package com.peryite.journeyd3.mvp.chapter.contract;

import com.peryite.journeyd3.mvp.BaseView;

public interface ChapterContract {

    interface Presenter {

        void resetChapter();

        void updateChapter();
    }

    interface View  extends BaseView<Presenter> {

        void reset();

        void update();
    }
}
