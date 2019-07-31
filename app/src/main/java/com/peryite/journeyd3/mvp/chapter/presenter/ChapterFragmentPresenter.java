package com.peryite.journeyd3.mvp.chapter.presenter;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.api.DataBaseApi;
import com.peryite.journeyd3.api.ParserApi;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.mvp.chapter.contract.ChapterContract;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppAllComponent;
import com.peryite.journeyd3.utils.Constant;
import com.peryite.journeyd3.utils.Parser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

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
        //        dataBaseApi = DataBaseConverter.getInstance(getContext());
//        if (chapterList.isEmpty()) {
//            new ChapterLoaderTask().execute();
//        } else {
//            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
//            adapter.setChapterService(chapterService);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(linearLayoutManager);
//        }
    }

    @Override
    public void showRestartDialog() {
        view.showDialog(MESSAGE_QUESTION_RESTART, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                view.restart();
            }
        });
    }

    @Override
    public void restart() {
        new ChapterRestartTask().execute();
    }

    @Override
    public void updateChapter() {
        view.update();
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
//            chapterList.clear();
//            showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
            view.createAdapterWithChapters(chapters);
            view.hideProgressBar();
            view.showRecyclerView();
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            hideProgressBar();
//            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDataBase() {
        List<Chapter> chapters = fillChapterListFromParser();
        dataBaseApi.clear();
        dataBaseApi.fillDataBase(chapters);
//        view.setChapters(dataBaseApi.getAllChapters());
//        saveTitle();
    }



    private List<Chapter> fillChapterListFromParser() {
        List<Chapter> chapters = Parser.getInstance().getChaptersAndTasksArray();
        return chapters;
    }

    class ChapterRestartTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            dataBaseApi.getTaskDAO().restart();
//            view.setChapters(dataBaseApi.getAllChapters());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.hideRecyclerView();
            view.showProgressBar();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.hideProgressBar();
            view.showRecyclerView();
        }
    }
//    class ChapterRestartTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void doInBackground(Void... voids) {
//            dataBaseApi.getTaskDAO().restart();
//            chapterList = fillChapterListFromDataBase();
//            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
//            adapter.setChapterService(chapterService);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            recyclerView.setVisibility(View.GONE);
//            showProgressBar();
//        }
//
//        @Override
//        protected void onPostExecute(List<Chapter> chapters) {
//            super.onPostExecute(chapters);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            hideProgressBar();
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//    }
}
