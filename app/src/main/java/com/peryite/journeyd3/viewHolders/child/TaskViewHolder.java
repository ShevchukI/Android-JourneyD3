package com.peryite.journeyd3.viewHolders.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskViewHolder extends ChildViewHolder {
    private final static String LOG_TAG = TaskViewHolder.class.getSimpleName();

    @BindView(R.id.chbx_task_complete)
    AppCompatCheckBox taskComplete;
//    @BindView(R.id.tv_task_name)
//    AppCompatTextView taskName;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

//        public void bind(int parentPosition, int childPosition) {
//            taskComplete.setChecked(chapters.get(parentPosition).getTasks().get(childPosition).isDone());
//            taskName.setText(chapters.get(parentPosition).getTasks().get(childPosition).getName());
//        }

    public void bind(Task task) {
        taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick: " + task.getName() + ": " + task.isDone());
            }
        });
        taskComplete.setChecked(task.isDone());
        taskComplete.setText(task.getName());
    }


}