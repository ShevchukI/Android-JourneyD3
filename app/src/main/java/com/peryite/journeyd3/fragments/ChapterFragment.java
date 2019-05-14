package com.peryite.journeyd3.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
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
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler(Looper.getMainLooper());
        dbHelper = new DBHelper(getActivity());
        chapterList = dbHelper.getAllChapters();
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        mainLL = view.findViewById(R.id.mainLinearLayout);
        start = view.findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startJourney();
            }
        });
        createViewChaptersAndTasks();
        return view;
    }

    private void startJourney() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                parser = new ParserDocument();
                dbHelper.clearAllTables();
                dbHelper.fillDatabase(parser.getChaptersAndTasksArray());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        chapterList = dbHelper.getAllChapters();
                        mainLL.removeAllViews();
                        createViewChaptersAndTasks();
//                        LogTag.printLogArray(LogTag.RESULT, chapters);
                    }
                });
            }

        }).start();
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
        LinearLayout.LayoutParams layoutParams = getLayoutParamByGravity(Gravity.CENTER);
        TextView tvChapter = new TextView(getActivity());
        tvChapter.setId(chapter.getId());
        tvChapter.setText(chapter.getName());
        mainLL.addView(tvChapter, layoutParams);
    }

    private void createChapterTaskCheckBox(ChapterTask chapterTask) {
        LinearLayout.LayoutParams layoutParams = getLayoutParamByGravity(Gravity.LEFT);
        final CheckBox cbChapterTask = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.Checkbox), null, 0);
//        final CheckBox cbChapterTask = new CheckBox(getActivity());
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
                            return;
                        }
                    }
                }
            }
        });
        mainLL.addView(cbChapterTask, layoutParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveJourney();
    }

    private void saveJourney() {
        dbHelper.updateChapter(chapterList);
    }

    private LinearLayout.LayoutParams getLayoutParamByGravity(int gravity) {
        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = gravity;
        layoutParams.topMargin = 20;
        return layoutParams;
    }

}
