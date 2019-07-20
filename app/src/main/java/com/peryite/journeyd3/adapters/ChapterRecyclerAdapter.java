package com.peryite.journeyd3.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppChapterComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class ChapterRecyclerAdapter extends ExpandableRecyclerAdapter<Chapter, Task,
        ChapterRecyclerAdapter.ChapterViewHolder, ChapterRecyclerAdapter.TaskViewHolder> {


    ChapterService chapterService;

    private Context context;
    private LayoutInflater inflater;

    public ChapterRecyclerAdapter(@NonNull List<Chapter> parentList, Context context) {
        super(parentList);
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.chapter_list_item, parentViewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChapterRecyclerAdapter.class.getSimpleName(), "bind: parent " );
            }
        });
        return new ChapterViewHolder(view);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.task_list_item, childViewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChapterRecyclerAdapter.class.getSimpleName(), "bind: child " );
            }
        });
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ChapterViewHolder parentViewHolder, int parentPosition, @NonNull Chapter parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull TaskViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Task child) {
        childViewHolder.bind(child);
    }

    public class ChapterViewHolder extends ParentViewHolder {
//        @BindView(R.id.tv_chapter_name)
        AppCompatTextView chapterName;
//        @BindView(R.id.tv_chapter_count_done)
        AppCompatTextView chapterCountDone;
//        @BindView(R.id.iv_arrow)
//        AppCompatImageView imageArrow;

        View view;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            chapterName = view.findViewById(R.id.tv_chapter_name);
            chapterCountDone = view.findViewById(R.id.tv_chapter_count_done);
        }

        public void bind(Chapter chapter) {
            chapterName.setText(chapter.getName());
            chapterCountDone.setText(view.getResources().getString(R.string.chapter_complete_vs_all_task,
                    chapterService.getCountChapterDoneTask(chapter),
                    chapterService.getCountChapterAllTask(chapter)));
        }
    }

    public class TaskViewHolder extends ChildViewHolder {
//        @BindView(R.id.chbx_task_complete)
        AppCompatCheckBox taskComplete;
//        @BindView(R.id.tv_task_name)
        AppCompatTextView taskName;

        View view;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            taskComplete = view.findViewById(R.id.chbx_task_complete);
            taskName = view.findViewById(R.id.tv_task_name);
        }

        public void bind(Task task){
            taskComplete.setChecked(task.isDone());
            taskName.setText(task.getName());
        }
    }

    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }
}
