package com.peryite.journeyd3.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.presenters.ChapterFragmentPresenter;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppAllComponent;
import com.peryite.journeyd3.utils.Parser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentChapter extends Fragment implements ChapterContract.View {

    private final static String LOG_TAG = FragmentChapter.class.getSimpleName();

    @BindView(R.id.rv_chapter_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    private LinearLayoutManager linearLayoutManager;


    private Unbinder unbinder;

    private ChapterContract.Presenter presenter;

    private View view;

    @Inject
    ChapterService chapterService;

    private ChapterRecyclerAdapter adapter;
    private List<Chapter> chapterList;

    public FragmentChapter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initVariable();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        presenter.initViews();
        return view;
    }

    private void initVariable() {
        AppAllComponent.getChapterComponent().injectsChapterService(this);
        presenter = new ChapterFragmentPresenter(this);
        chapterList = new ArrayList<>();
    }

    @Override
    public void initViews() {
        if (chapterList.isEmpty()) {
            new ChapterLoaderTask().execute();
        } else {
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public void reset() {
        new AlertDialog.Builder(getContext())
                .setMessage("Restart this season?")
                .setCancelable(true)
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTasks();
                    }
                })
                .setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean checkDataBaseRecords() {
        return DBHelper.getInstance(getContext()).checkRecords();
    }

    private List<Chapter> fillChapterListFromDataBase() {
        return DBHelper.getInstance(getContext()).getAllChapters();
    }

    private List<Chapter> fillChapterListFromParser() {
        return Parser.getInstance().getChaptersAndTasksArray();
    }

    public void updateDataBase() {
        chapterList = fillChapterListFromParser();
        DBHelper.getInstance(getContext()).deleteAllRecords();
        DBHelper.getInstance(getContext()).fillDatabase(chapterList);
    }

    public void resetTasks() {
        new ChapterRestartTask().execute();
    }

    class ChapterLoaderTask extends AsyncTask<Void, Void, List<Chapter>> {
        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            if (checkDataBaseRecords()) {
                chapterList = fillChapterListFromDataBase();
            } else {
                updateDataBase();
            }
            return chapterList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chapterList.clear();
            showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
                adapter = new ChapterRecyclerAdapter(chapterList, getContext());
                adapter.setChapterService(chapterService);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            hideProgressBar();
        }
    }

    class ChapterRestartTask extends AsyncTask<Void, Void, List<Chapter>> {

        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            synchronized (adapter) {
                DBHelper.getInstance(getContext()).resetAllTasks();
                chapterList = fillChapterListFromDataBase();
            }
            return chapterList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            showProgressBar();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            super.onPostExecute(chapters);
            synchronized (adapter) {
                adapter = new ChapterRecyclerAdapter(chapterList, getContext());
                adapter.setChapterService(chapterService);
                recyclerView.setAdapter(adapter);
                hideProgressBar();
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

    }
}
