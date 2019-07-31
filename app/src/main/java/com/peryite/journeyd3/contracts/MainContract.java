package com.peryite.journeyd3.contracts;

import android.support.v4.app.Fragment;

public interface MainContract {

    interface Presenter {
        void start();

        void onClickChapter(int id_navigator);

        void onClickConquest(int id_navigator);

        void onClickReward(int id_navigator);

        void onClickCredits(int id_navigator);

        void onClickRestart();

        void onClickUpdate();

        void showTitle();
    }

    interface View {
        void start();

        void selectFragment(Fragment fragment, int id_fragment_navigator);

        void showTitle(String title);

        void restartChapter();

        void updateChapter();
    }
}
