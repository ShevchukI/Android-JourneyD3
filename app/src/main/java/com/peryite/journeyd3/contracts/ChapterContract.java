package com.peryite.journeyd3.contracts;

public interface ChapterContract {
    interface Model {

    }

    interface Presenter {

        void resetChapter();

        void updateChapter();
    }

    interface View {

        void reset();

        void update();
    }
}
