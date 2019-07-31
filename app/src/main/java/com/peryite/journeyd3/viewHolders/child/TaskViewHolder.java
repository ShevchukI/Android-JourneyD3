package com.peryite.journeyd3.viewHolders.child;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.peryite.journeyd3.DBHelper.DataBaseConverter;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.api.DataBaseApi;
import com.peryite.journeyd3.entities.TaskEntity;
import com.peryite.journeyd3.listeners.OnChapterRecyclerAdapterListener;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskViewHolder extends ChildViewHolder {
    private final static String LOG_TAG = TaskViewHolder.class.getSimpleName();

    OnChapterRecyclerAdapterListener listener;
    DataBaseApi dataBaseApi;

    private Context context;

    @BindView(R.id.chbx_task_complete)
    AppCompatCheckBox taskComplete;

    public TaskViewHolder(@NonNull View itemView, OnChapterRecyclerAdapterListener listener, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        this.context = context;
        dataBaseApi = DataBaseConverter.getInstance(context);
    }

    public void bind(List<Chapter> chapters, int chapterPosition, int taskPosition) {
        taskComplete.setChecked(chapters.get(chapterPosition).getTasks().get(taskPosition).isComplete());
        taskComplete.setText(chapters.get(chapterPosition).getTasks().get(taskPosition).getName());
        taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapters.get(chapterPosition).getTasks().get(taskPosition).setComplete(taskComplete.isChecked());
                //TODO
                TaskEntity taskEntity = convertTask(chapters.get(chapterPosition).getTasks().get(taskPosition), chapters.get(chapterPosition).getId());
                new UpdateTask().execute(taskEntity);
                listener.notifyParentByPosition(chapterPosition);
                Log.d(LOG_TAG, "onClick: " + chapters.get(chapterPosition).getName());
                Log.d(LOG_TAG, "onClick: " + chapters.get(chapterPosition).getTasks().get(taskPosition).getName() + ": " + chapters.get(chapterPosition).getTasks().get(taskPosition).isComplete());
            }
        });
    }

    class UpdateTask extends AsyncTask<TaskEntity, Void, Void> {

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            dataBaseApi.getTaskDAO().update(taskEntities[0]);
            return null;
        }

    }

    private TaskEntity convertTask(Task task, long chapterId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setName(task.getName());
        taskEntity.setComplete(task.isComplete());
        taskEntity.setChapterId(chapterId);
        return taskEntity;
    }
}