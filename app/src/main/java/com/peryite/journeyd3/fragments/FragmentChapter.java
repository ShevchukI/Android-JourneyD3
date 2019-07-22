package com.peryite.journeyd3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppAllComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentChapter.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentChapter#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentChapter extends Fragment implements ChapterContract.View {

    private static final String ARG_CHAPTER_LIST = "chapterList";

    @BindView(R.id.rv_chapter_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    private Unbinder unbinder;

    View view;

    ChapterRecyclerAdapter adapter;

    @Inject
    ChapterService chapterService;

    private List<Chapter> chapterList;

//    private OnFragmentInteractionListener mListener;

    public FragmentChapter() {
        // Required empty public constructor
    }


    public static FragmentChapter newInstance(List<Chapter> chapterList) {
        FragmentChapter fragment = new FragmentChapter();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CHAPTER_LIST, (ArrayList<? extends Parcelable>) chapterList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fillChapterList();
//        if (getArguments() != null) {
//            chapterList = getArguments().getParcelableArrayList(ARG_CHAPTER_LIST);
//            getArguments().remove(ARG_CHAPTER_LIST);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        unbinder = ButterKnife.bind(this, view);
        AppAllComponent.getChapterComponent().injectsChapterService(this);
//        fillChapterList();
//        if (getArguments() != null) {
//            chapterList = getArguments().getParcelableArrayList(ARG_CHAPTER_LIST);
//            getArguments().remove(ARG_CHAPTER_LIST);
//        }

//        if (chapterList != null) {
//            initViews();
//        } else {
//            chapterList = new ArrayList<>();
//            initViews();
//        }
        return view;
    }

    private void fillChapterList() {
        assert getArguments() != null;
        if (getArguments().getParcelableArrayList(ARG_CHAPTER_LIST) != null) {
            chapterList = getArguments().getParcelableArrayList(ARG_CHAPTER_LIST);
            getArguments().remove(ARG_CHAPTER_LIST);
        }
    }

    public void initViews() {
        adapter = new ChapterRecyclerAdapter(chapterList, getContext());
        adapter.setChapterService(chapterService);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//
////        void onFragmentInteraction(LinearLayout chapterLinear);
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
