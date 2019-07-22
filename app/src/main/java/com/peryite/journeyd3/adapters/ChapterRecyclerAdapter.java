package com.peryite.journeyd3.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.listeners.OnChapterRecyclerAdapterListener;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.viewHolders.child.TaskViewHolder;
import com.peryite.journeyd3.viewHolders.parent.ChapterViewHolder;

import java.util.List;


public class ChapterRecyclerAdapter extends ExpandableRecyclerAdapter<Chapter, Task,
        ChapterViewHolder, TaskViewHolder> {
    private final static String LOG_TAG = ChapterRecyclerAdapter.class.getSimpleName();

    OnChapterRecyclerAdapterListener listener;
    ChapterService chapterService;
    List<Chapter> chapters;
    Context context;
    private LayoutInflater inflater;

    public ChapterRecyclerAdapter(@NonNull List<Chapter> parentList, Context context) {
        super(parentList);
        this.chapters = parentList;
        this.context = context;
        listener = new OnChapterRecyclerAdapterListener() {
            @Override
            public void notifyParentByPosition(int parentPosition) {
                notifyItemChanged(parentPosition);
            }
        };
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.chapter_list_item, parentViewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChapterRecyclerAdapter.class.getSimpleName(), "bind: parent ");
            }
        });

        return new ChapterViewHolder(view, chapterService);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.task_list_item, childViewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChapterRecyclerAdapter.class.getSimpleName(), "bind: child ");
            }
        });
        return new TaskViewHolder(view, listener, context);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ChapterViewHolder parentViewHolder, int parentPosition, @NonNull Chapter parent) {
        parentViewHolder.bind(parent);
        parentViewHolder.itemView.setOnClickListener(v -> {
            Log.d(LOG_TAG, "onClick: parent");
            parentViewHolder.expandCollapse();
        });

    }

    @Override
    public void onBindChildViewHolder(@NonNull TaskViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Task child) {
        childViewHolder.bind(chapters, parentPosition, childPosition);
    }

    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }


}
