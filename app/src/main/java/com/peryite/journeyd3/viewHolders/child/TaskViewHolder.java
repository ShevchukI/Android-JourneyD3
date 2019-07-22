package com.peryite.journeyd3.viewHolders.child;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.listeners.OnChapterRecyclerAdapterListener;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TaskViewHolder extends ChildViewHolder {
    private final static String LOG_TAG = TaskViewHolder.class.getSimpleName();

    OnChapterRecyclerAdapterListener listener;

    private Context context;

    @BindView(R.id.chbx_task_complete)
    AppCompatCheckBox taskComplete;
//    @BindView(R.id.tv_task_name)
//    AppCompatTextView taskName;


    public TaskViewHolder(@NonNull View itemView, OnChapterRecyclerAdapterListener listener, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        this.context = context;
    }

//        public void bind(int parentPosition, int childPosition) {
//            taskComplete.setChecked(chapters.get(parentPosition).getTasks().get(childPosition).isDone());
//            taskName.setText(chapters.get(parentPosition).getTasks().get(childPosition).getName());
//        }

    public void bind(Task task) {
        taskComplete.setChecked(task.isDone());
        taskComplete.setText(task.getName());
//        taskComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                task.setDone(taskComplete.isChecked());
//                Log.d(LOG_TAG, "onClick: " + task.getName() + ": " + task.isDone());
//            }
//        });
//        taskComplete.setChecked(task.isDone());
//        taskComplete.setText(task.getName());
    }

    public void bind(List<Chapter> chapters, int chapterPosition, int taskPosition) {
        taskComplete.setChecked(chapters.get(chapterPosition).getTasks().get(taskPosition).isDone());
        taskComplete.setText(chapters.get(chapterPosition).getTasks().get(taskPosition).getName());
        taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapters.get(chapterPosition).getTasks().get(taskPosition).setDone(taskComplete.isChecked());
                DBHelper.getInstance(context).updateTask(chapters.get(chapterPosition).getTasks().get(taskPosition));
                listener.notifyParentByPosition(chapterPosition);
                Log.d(LOG_TAG, "onClick: " + chapters.get(chapterPosition).getName());
                Log.d(LOG_TAG, "onClick: " + chapters.get(chapterPosition).getTasks().get(taskPosition).getName() + ": " + chapters.get(chapterPosition).getTasks().get(taskPosition).isDone());
            }
        });
    }


}