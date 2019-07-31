package com.peryite.journeyd3.viewHolders.parent;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.services.ChapterService;
import com.peryite.journeyd3.utils.AppAllComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterViewHolder extends ParentViewHolder {
    @BindView(R.id.tv_chapter_name)
    AppCompatTextView chapterName;
    @BindView(R.id.tv_chapter_count_done)
    AppCompatTextView chapterCountDone;

    @Inject
    ChapterService chapterService;

    public ChapterViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        AppAllComponent.getChapterComponent().injectsChapterService(this);
    }

    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

    public void expandCollapse() {
        if (isExpanded()) {
            collapseView();
        } else {
            expandView();
        }

    }

    public void bind(Chapter chapter) {
        chapterName.setText(chapter.getName());
            notifyCompletedTask(chapter);
        chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
                chapterService.getCountChapterDoneTask(chapter),
                chapterService.getCountChapterAllTask(chapter)));

    }

    public void notifyCompletedTask(Chapter chapter) {
        chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
                chapterService.getCountChapterDoneTask(chapter),
                chapterService.getCountChapterAllTask(chapter)));
    }
}