package com.peryite.journeyd3.mvp.chapter.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.mvp.chapter.contract.ChapterContract;
import com.peryite.journeyd3.models.Chapter;

import java.util.List;

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

    private ChapterRecyclerAdapter adapter;

    public FragmentChapter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        presenter.start();
        return view;
    }

    @Override
    public void createAdapterWithChapters(List<Chapter> chapters) {
        adapter = new ChapterRecyclerAdapter(chapters, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(R.string.button_yes, okListener)
                .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    @Override
    public void setPresenter(ChapterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
