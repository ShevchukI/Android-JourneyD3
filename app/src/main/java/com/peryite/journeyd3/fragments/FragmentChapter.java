package com.peryite.journeyd3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.models.Chapter;
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

    @BindView(R.id.rv_chapter_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    private Unbinder unbinder;

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
        initVariable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
//        if (chapterList.size() == 0) {
//            new ChapterLoaderTask().execute();
//        } else {
//            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
//            adapter.setChapterService(chapterService);
//            recyclerView.setAdapter(adapter);
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    private void initVariable() {
        AppAllComponent.getChapterComponent().injectsChapterService(this);
        chapterList = new ArrayList<>();
    }

    private void initViews() {
        if (chapterList.size() == 0) {
            new ChapterLoaderTask().execute();
        } else {
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
////        new ChapterLoaderTask().execute();
//        if (checkDataBaseRecords()) {
//            chapterList = fillChapterListFromDataBase();
//        } else {
//            updateDataBase();
//        }
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
            hideProgressBar();
        }
    }

}
