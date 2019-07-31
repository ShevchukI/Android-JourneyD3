package com.peryite.journeyd3.mvp.main.presenter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.peryite.journeyd3.api.ParserApi;
import com.peryite.journeyd3.mvp.main.contract.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.utils.Constant;
import com.peryite.journeyd3.utils.LogTag;
import com.peryite.journeyd3.utils.Parser;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    private SharedPreferences preferences;

    private ParserApi parser;

    public MainPresenter(MainContract.View view, SharedPreferences preferences) {
        this.view = view;
        this.preferences = preferences;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.start();
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
        view.restartChapter();
    }

    @Override
    public void onClickUpdate() {
        Log.d(LogTag.CLICK, "Click: update button ");
        view.updateChapter();
    }

    @Override
    public void showTitle() {
        new TitleLoader().execute();

    }

    private String getTitleFromShare() {
        String title = preferences.getString(Constant.SHARED_TITLE, "SEASON");
        return title;
    }

    private boolean titleShareIsEmpty() {
        if (getTitleFromShare().equals("SEASON")) {
            return true;
        } else {
            return false;
        }
    }

    private void saveTitleFromParser() {
        preferences.edit().putString(Constant.SHARED_TITLE, parser.getTitle()).apply();
    }

    class TitleLoader extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            if(parser == null) {
                parser = Parser.getInstance();
            }
            String title;
            if (titleShareIsEmpty()) {
                saveTitleFromParser();
            }
            title = getTitleFromShare();
            return title;
        }

        @Override
        protected void onPostExecute(String title) {
            super.onPostExecute(title);
            view.showTitle(title);
        }
    }
}
