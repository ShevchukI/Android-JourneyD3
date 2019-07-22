package com.peryite.journeyd3.contracts;

public interface ChapterContract {
    interface Model {

    }

    interface Presenter {
        void initViews();

        void resetChapter();
    }

    interface View {
        void initViews();

        void reset();
    }
}
