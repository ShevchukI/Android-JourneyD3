package com.peryite.journeyd3.mvp.chapter.presenter;

import android.content.DialogInterface;
import android.os.AsyncTask;

import com.peryite.journeyd3.api.DataBaseApi;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.mvp.chapter.contract.ChapterContract;
import com.peryite.journeyd3.utils.Parser;

import java.util.List;


public class ChapterFragmentPresenter implements ChapterContract.Presenter {
    private final static String MESSAGE_QUESTION_RESTART = "Restart this season?";
    private final static String MESSAGE_QUESTION_UPDATE = "Update current season (download from website)?";

    private ChapterContract.View view;

    private DataBaseApi dataBaseApi;


    public ChapterFragmentPresenter(ChapterContract.View view, DataBaseApi dataBaseApi) {
        this.view = view;
        this.dataBaseApi = dataBaseApi;


        view.setPresenter(this);
    }

    @Override
    public void start() {
        new ChapterLoaderTask().execute();
    }

    @Override
    public void restart() {
        view.showDialog(MESSAGE_QUESTION_RESTART, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ChapterRestartTask().execute();
            }
        });
    }

    @Override
    public void update() {
        view.showDialog(MESSAGE_QUESTION_UPDATE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ChapterUpdateTask().execute();
            }
        });
    }

    class ChapterLoaderTask extends AsyncTask<Void, Void, List<Chapter>> {
        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            if (dataBaseApi.isEmptyDataBase()) {
                updateDataBase();
            }
            return dataBaseApi.getAllChapters();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.hideRecyclerView();
            view.showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
            view.createAdapterWithChapters(chapters);
            view.hideProgressBar();
            view.showRecyclerView();

        }
    }

    private List<Chapter> fillChapterListFromParser() {
        List<Chapter> chapters = Parser.getInstance().getChaptersAndTasksArray();
        return chapters;
    }

    class ChapterRestartTask extends AsyncTask<Void, Void, List<Chapter>> {

        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            dataBaseApi.getTaskDAO().restart();
            return dataBaseApi.getAllChapters();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.hideRecyclerView();
            view.showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
            view.createAdapterWithChapters(chapters);
            view.hideProgressBar();
            view.showRecyclerView();
        }
    }

    class ChapterUpdateTask extends AsyncTask<Void, Void, List<Chapter>> {

        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            updateDataBase();
            return dataBaseApi.getAllChapters();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.hideRecyclerView();
            view.showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
            view.createAdapterWithChapters(chapters);
            view.hideProgressBar();
            view.showRecyclerView();
        }
    }

    private void updateDataBase() {
        List<Chapter> chapters = fillChapterListFromParser();
        dataBaseApi.clear();
        dataBaseApi.fillDataBase(chapters);
    }
}
