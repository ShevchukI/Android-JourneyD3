package com.peryite.journeyd3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppChapterComponent;
import com.peryite.journeyd3.utils.LogTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentChapter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentChapter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChapter extends Fragment {

    private static final String ARG_CHAPTER_LIST = "chapterList";

    @BindView(R.id.rv_chapter_recycler)
    RecyclerView recyclerView;
    //    RecyclerView recyclerView;
    View view;

    ChapterRecyclerAdapter adapter;

    //    @Inject
    ChapterService chapterService;

    private List<Chapter> chapterList;

    private OnFragmentInteractionListener mListener;
    private LinearLayout chapterLinear;

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
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//            chapterList = (ArrayList<Chapter>) getArguments().getSerializable(ARG_CHAPTER_LIST);
//            getArguments().remove(ARG_CHAPTER_LIST);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mListener.onFragmentInteraction("test fragment interaction!");
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        ButterKnife.bind(this, view);
//        chapterLinear = view.findViewById(R.id.chapter_linear);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            chapterList = getArguments().getParcelableArrayList(ARG_CHAPTER_LIST);
            getArguments().remove(ARG_CHAPTER_LIST);
        }
        mListener.onFragmentInteraction(chapterLinear);
//        chapterLinear.removeAllViews();
        if (chapterList != null) {
//            createFields();
            initViews();
        }
//        initViews();
        return view;
    }

    public void initViews() {
//        AppChapterComponent.getComponent().injectsChapterService(this);
        chapterService = new ChapterService();
//        recyclerView = view.findViewById(R.id.rv_chapter_recycler);
        adapter = new ChapterRecyclerAdapter(chapterList, getContext());
        adapter.setChapterService(chapterService);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onFragmentInteraction(LinearLayout chapterLinear);
    }

}
