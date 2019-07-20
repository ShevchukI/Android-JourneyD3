package com.peryite.journeyd3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentChapter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentChapter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChapter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_CHAPTER_LIST = "chapterList";

    //    @BindView(R.id.rv_chapter_recycler)
//    RecyclerView recyclerView;
    RecyclerView recyclerView;
    View view;

    ChapterRecyclerAdapter adapter;

//    @Inject
    ChapterService chapterService;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Chapter> chapterList;

    private OnFragmentInteractionListener mListener;
    private LinearLayout chapterLinear;

    public FragmentChapter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentChapter.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentChapter newInstance(String param1, String param2) {
        FragmentChapter fragment = new FragmentChapter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentChapter newInstance(List<Chapter> chapterList) {
        FragmentChapter fragment = new FragmentChapter();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAPTER_LIST, (Serializable) chapterList);
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
//        chapterLinear = view.findViewById(R.id.chapter_linear);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            chapterList = (ArrayList<Chapter>) getArguments().getSerializable(ARG_CHAPTER_LIST);
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
        recyclerView = view.findViewById(R.id.rv_chapter_recycler);
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

        void onFragmentInteraction(String text);

        void onFragmentInteraction(List<Chapter> chapterArrayList);

        void onFragmentInteraction(LinearLayout chapterLinear);
    }

    private void createFields() {
        for (Chapter chapter : chapterList) {
            createChapterLabel(chapter);
            for (Task task : chapter.getTasks()) {
                createChapterTaskCheckBox(task);
            }
        }
    }

    private void createChapterLabel(Chapter chapter) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        TextView tvChapter = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextView), null, 0);
        tvChapter.setId(chapter.getId());
        tvChapter.setText(chapter.getName());
        layoutParams.setMargins(0, 15, 0, 15);
        chapterLinear.addView(tvChapter, layoutParams);
    }

    private void createChapterTaskCheckBox(Task task) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final CheckBox cbTask = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBox), null, 0);
//        cbChapterTask.setButtonDrawable(R.drawable.checkbox);
        layoutParams.setMargins(0, 15, 0, 15);
        cbTask.setId(task.getId());
        cbTask.setText(task.getName());
        if (task.isDone()) {
            cbTask.setChecked(true);
        } else {
            cbTask.setChecked(false);
        }
        cbTask.setOnClickListener(v -> {
            for (Chapter chapter : chapterList) {
                for (Task chapterTask : chapter.getTasks()) {
                    if (cbTask.getId() == chapterTask.getId()) {
                        chapterTask.setDone(cbTask.isChecked());
                        Log.d(LogTag.CLICK, "Task (id: " + chapterTask.getId() + " , name: " + chapterTask.getName()
                                + ") changed! New status: " + chapterTask.isDone());
                        mListener.onFragmentInteraction(chapterList);
                        return;
                    }
                }
            }
        });
        chapterLinear.addView(cbTask, layoutParams);
    }

}
