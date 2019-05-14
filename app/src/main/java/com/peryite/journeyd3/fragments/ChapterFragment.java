package com.peryite.journeyd3.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.ChapterTask;
import com.peryite.journeyd3.tools.LogTag;
import com.peryite.journeyd3.tools.ParserDocument;

import java.util.ArrayList;

public class ChapterFragment extends Fragment {
    private Handler mHandler;
    private DBHelper dbHelper;
    private ParserDocument parser;
    private Button start;
    private LinearLayout mainLL;
    private ArrayList<Chapter> chapterList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        mainLL = view.findViewById(R.id.mainLinearLayout);
        dbHelper = new DBHelper(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            chapterList = (ArrayList<Chapter>) bundle.getSerializable(Chapter.TOKEN);
            createViewChaptersAndTasks();
        } else {
            chapterList = dbHelper.getAllChapters();
            createViewChaptersAndTasks();
        }
        mHandler = new Handler(Looper.getMainLooper());
        start = view.findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startJourney();
            }
        });
        return view;
    }

    private void startJourney() {
        Log.d(LogTag.CLICK, "startJourney: click! operation start!");
        mainLL.removeAllViews();
        Log.d(LogTag.CLICK, "startJourney: remove all views!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                parser = new ParserDocument();
                Log.d(LogTag.CLICK, "startJourney: parser create!");
                dbHelper.clearAllTables();
                Log.d(LogTag.CLICK, "startJourney: database clear!");
                dbHelper.fillDatabase(parser.getChaptersAndTasksArray());
                Log.d(LogTag.CLICK, "startJourney: database filled!");
                chapterList = dbHelper.getAllChapters();
                Log.d(LogTag.CLICK, "startJourney: chapterList filled!");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createViewChaptersAndTasks();
                        Log.d(LogTag.CLICK, "startJourney: new elements created!");
                    }
                });
                Log.d(LogTag.RESULT, String.valueOf(chapterList.size()));
            }

        }).start();
        Log.d(LogTag.CLICK, "startJourney: operation finished!");
    }

    private void createViewChaptersAndTasks() {
        for (Chapter chapter : chapterList) {
            createChapterLabel(chapter);
            for (ChapterTask chapterTask : chapter.getTasks()) {
                createChapterTaskCheckBox(chapterTask);
            }
        }

    }

    private void createChapterLabel(Chapter chapter) {
        LinearLayout.LayoutParams layoutParams = getLayoutParamByGravity(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        layoutParams.topMargin = 50;
        TextView tvChapter = new TextView(new ContextThemeWrapper(getActivity(), R.style.ChapterLabel), null, 0);
        tvChapter.setId(chapter.getId());
        tvChapter.setText(chapter.getName());
        mainLL.addView(tvChapter, layoutParams);
    }

    private void createChapterTaskCheckBox(ChapterTask chapterTask) {
        LinearLayout.LayoutParams layoutParams = getLayoutParamByGravity(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT);
        final CheckBox cbChapterTask = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.Checkbox), null, 0);
        cbChapterTask.setId(chapterTask.getId());
        cbChapterTask.setText(chapterTask.getName());
        if (chapterTask.isDone()) {
            cbChapterTask.setChecked(true);
        } else {
            cbChapterTask.setChecked(false);
        }
        cbChapterTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Chapter chapter : chapterList) {
                    for (ChapterTask chapterTask : chapter.getTasks()) {
                        if (cbChapterTask.getId() == chapterTask.getId()) {
                            chapterTask.setDone(cbChapterTask.isChecked());
                            Log.d(LogTag.CLICK, "Task (id: " + chapterTask.getId() + " , name: " + chapterTask.getName()
                                    + ") changed! New status: " + chapterTask.isDone());
                            return;
                        }
                    }
                }
            }
        });
        mainLL.addView(cbChapterTask, layoutParams);
    }

    @Override
    public void onPause() {
        saveJourney();
        Log.d(LogTag.RESULT, "onPause start");
        super.onPause();
        Log.d(LogTag.RESULT, "onPause end");
    }

    @Override
    public void onStop() {
        Log.d(LogTag.RESULT, "onStop start");
        super.onStop();
        Log.d(LogTag.RESULT, "onStop end");
    }

    @Override
    public void onResume() {
        Log.d(LogTag.RESULT, "onResume start");
        super.onResume();
        Log.d(LogTag.RESULT, "onResume end");
    }

    @Override
    public void onDestroy() {
        Log.d(LogTag.RESULT, "onDestroy start");
        saveJourney();
        super.onDestroy();
        Log.d(LogTag.RESULT, "onDestroy end");
    }

    private void saveJourney() {
        dbHelper.updateChapter(chapterList);
        Log.d(DBHelper.NAME, "DataBase updated!");
    }

    private LinearLayout.LayoutParams getLayoutParamByGravity(int width, int height, int gravity) {
        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(width, height);
        layoutParams.gravity = gravity;
        layoutParams.topMargin = 20;
        return layoutParams;
    }

}
