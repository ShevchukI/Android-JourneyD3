package com.peryite.journeyd3.presenters;

import android.util.Log;

import com.peryite.journeyd3.contracts.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.utils.LogTag;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter (MainContract.View view){
        this.view = view;
    }

    @Override
    public void start() {
        view.showMainFragment();
    }

    @Override
    public void onClickChapter(int id_navigator) {
        Log.d(LogTag.CLICK, "Click: chapter button ");
        view.selectFragment(FragmentManager.getInstance().getFragmentChapter(), id_navigator);
    }

    @Override
    public void onClickConquest(int id_navigator) {
        Log.d(LogTag.CLICK, "Click: conquest button ");
        view.selectFragment(FragmentManager.getInstance().getFragmentConquest(), id_navigator);
    }

    @Override
    public void onClickReward(int id_navigator) {
        Log.d(LogTag.CLICK, "Click: reward button ");
        view.selectFragment(FragmentManager.getInstance().getFragmentReward(), id_navigator);
    }

    @Override
    public void onClickCredits(int id_navigator) {
        Log.d(LogTag.CLICK, "Click: credits button ");
        view.selectFragment(FragmentManager.getInstance().getFragmentCredits(), id_navigator);
    }

    @Override
    public void onClickRestart() {
        Log.d(LogTag.CLICK, "Click: restart button ");
        view.resetChapter();
    }

    @Override
    public void onClickUpdate() {
        Log.d(LogTag.CLICK, "Click: update button ");
        view.updateChapter();
    }
}
