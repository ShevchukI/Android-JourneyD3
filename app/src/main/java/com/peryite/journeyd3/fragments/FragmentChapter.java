package com.peryite.journeyd3.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.DBHelper.DAO.TaskDAO;
import com.peryite.journeyd3.DBHelper.JourneyDB;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.presenters.ChapterFragmentPresenter;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppAllComponent;
import com.peryite.journeyd3.utils.Constant;
import com.peryite.journeyd3.utils.Parser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

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

    private ChapterDAO chapterDAO;
    private TaskDAO taskDAO;

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
        chapterDAO = JourneyDB.getInstance(getContext()).chapterDAO();
        taskDAO = JourneyDB.getInstance(getContext()).taskDAO();
        if (chapterList.isEmpty()) {
            new ChapterLoaderTask().execute();
        } else {
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        //TODO
        new ChapterDAOTest().execute();
        return view;
    }

    @Override
    public void reset() {
        new AlertDialog.Builder(getContext())
                .setMessage("Restart this season?")
                .setCancelable(true)
                .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTasks();
                    }
                })
                .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    @Override
    public void update() {
        new AlertDialog.Builder(getContext())
                .setMessage("Update current season (download from website)?")
                .setCancelable(true)
                .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateTasks();
                    }
                })
                .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }


    class ChapterLoaderTask extends AsyncTask<Void, Void, List<Chapter>> {
        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            if (checkDataBaseRecords()) {
                chapterList = fillChapterListFromDataBase();
            } else {
                updateDataBase();
            }
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
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
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            hideProgressBar();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class ChapterRestartTask extends AsyncTask<Void, Void, List<Chapter>> {

        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            //TODO
//            DBHelper.getInstance(getContext()).resetAllTasks();
            chapterList = fillChapterListFromDataBase();
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
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
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            hideProgressBar();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class ChapterUpdateTask extends AsyncTask<Void, Void, List<Chapter>> {

        @Override
        protected List<Chapter> doInBackground(Void... voids) {
            updateDataBase();
            adapter = new ChapterRecyclerAdapter(chapterList, getContext());
            adapter.setChapterService(chapterService);
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
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            hideProgressBar();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class ChapterDAOTest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<ChapterEntity> chapterEntities = new ArrayList<>();

            ChapterEntity chapterEntity = new ChapterEntity();
            chapterEntity.setName("Chapter I");
            chapterEntities.add(chapterEntity);

            ChapterEntity chapterEntity2 = new ChapterEntity();
            chapterEntity2.setName("Chapter II");
            chapterEntities.add(chapterEntity2);

            chapterDAO.insert(chapterEntities);


            List<Chapter> chapters = chapterDAO.getAll();
            List<TaskEntity> taskEntities = new ArrayList<>();
            for (int i = 0; i < chapters.size(); i++) {
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setName("task from " + chapters.get(i).getName());
                taskEntity.setComplete(false);
                taskEntity.setChapterId(chapters.get(i).getId());
                taskEntities.add(taskEntity);
                taskDAO.insert(taskEntity);
            }
            taskEntities = taskDAO.getAllEntity();
            for (Chapter chapter : chapters) {
                Log.d(LOG_TAG, "doInBackground: Test DAO Chapter: " + chapter.getId() + " " + chapter.getName());
            }
            for (TaskEntity taskEntity : taskEntities) {
                Log.d(LOG_TAG, "doInBackground: Test DAO TASK: " + taskEntity.getId() + " " + taskEntity.getName()
                        + " " + taskEntity.isComplete() + " " + taskEntity.getChapterId());
            }

//            journeyDB.chapterDAO().insert(chapterEntity);
//            RewardEntity rewardEntity = new RewardEntity();
//            rewardEntity.setName("Test Reward");
//            rewardEntity.setComplete(false);
//            journeyDB.rewardDAO().insert(rewardEntity);
//            List<Chapter> testChapterList = journeyDB.chapterDAO().getAll();
//            for (Chapter chapter : testChapterList) {
//                Log.d(LOG_TAG, "doInBackground: Test DAO Chapter: " + chapter.getId() + " " + chapter.getName());
//            }
//            long testId = 1L;
//            Chapter chapter = journeyDB.chapterDAO().getById(testId);
//            Reward reward = journeyDB.rewardDAO().getById(testId);
//            Log.d(LOG_TAG, "doInBackground: Test DAT Chapter By Id " + testId + ": " + chapter.getId() + " " + chapter.getName());
//            Log.d(LOG_TAG, "doInBackground: Test DAT Reward By Id " + testId + ": " + reward.getId() + " " + reward.getName() + " " + reward.isComplete());
            return null;
        }
    }

    private void initVariable() {
        AppAllComponent.getChapterComponent().injectsChapterService(this);
        presenter = new ChapterFragmentPresenter(this);
        chapterList = new ArrayList<>();
    }

    private void saveTitle() {
        SharedPreferences sharedPreferences = this.getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.SHARED_TITLE, Parser.getInstance().getTitle());
        editor.apply();
    }

    private void updateDataBase() {
        //TODO
//        List<Chapter> chapters = fillChapterListFromParser();
//        DBHelper.getInstance(getContext()).deleteAllRecords();
//        DBHelper.getInstance(getContext()).fillDatabase(chapters);
//        chapterList = fillChapterListFromDataBase();
        saveTitle();
    }

    private void resetTasks() {
        //TODO
//        new ChapterRestartTask().execute();
    }

    private void updateTasks() {
        //TODO
//        new ChapterUpdateTask().execute();
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
        //TODO
//        boolean check = DBHelper.getInstance(getContext()).checkRecords();
        boolean check = false;
        return check;
    }

    private List<Chapter> fillChapterListFromDataBase() {
        //TODO
//        List<Chapter> chapters = DBHelper.getInstance(getContext()).getAllChapters();
        List<Chapter> chapters = new ArrayList<>();
        return chapters;
    }

    private List<Chapter> fillChapterListFromParser() {
        //TODO
//        List<Chapter> chapters = Parser.getInstance().getChaptersAndTasksArray();
        List<Chapter> chapters = new ArrayList<>();
        return chapters;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume: ");
    }
}
