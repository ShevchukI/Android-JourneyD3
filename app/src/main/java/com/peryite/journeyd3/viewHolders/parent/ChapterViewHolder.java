package com.peryite.journeyd3.viewHolders.parent;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.services.ChapterService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterViewHolder extends ParentViewHolder {
    @BindView(R.id.tv_chapter_name)
    AppCompatTextView chapterName;
    @BindView(R.id.tv_chapter_count_done)
    AppCompatTextView chapterCountDone;
//    @BindView(R.id.iv_arrow)
//    AppCompatImageView arrowImage;

    private boolean collapse;
    private ChapterService chapterService;

    public ChapterViewHolder(@NonNull View itemView, ChapterService chapterService) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.chapterService = chapterService;
//            chapterName.setOnClickListener(v -> expandCollapse());
    }


    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }


    public void expandCollapse() {
        if (isExpanded()) {
            collapseView();
//            rotateArrowDown();
        } else {
            expandView();
//            rotateArrowUp();
        }

    }

    public boolean isCollapse() {
        return !isExpanded();
    }

    public void expandCollapse(boolean collapse) {
        if (isExpanded()) {
            collapseView();
        } else {
            expandView();
        }
//        if (collapse) {
//            rotateArrowDown();
//        } else {
//            rotateArrowUp();
//        }
    }

    public void collapse() {
        collapseView();
    }

//    public void rotateArrowUp() {
//        arrowImage.animate().rotation(180);
//    }
//
//    public void rotateArrowDown() {
//        arrowImage.animate().rotation(0);
//    }
//
//    public void rotateArrow(float value){
//        arrowImage.animate().rotation(value);
//    }

//    public void rotateArrow() {
//        if (isExpanded()) {
//            rotateArrowDown();
////                arrowImage.animate().rotation(0);
//        } else {
//            rotateArrowUp();
////                arrowImage.animate().rotation(180);
//        }
//    }

    public void bind(Chapter chapter) {
        chapterName.setText(chapter.getName());
            notifyCompletedTask(chapter);
        chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
                chapterService.getCountChapterDoneTask(chapter),
                chapterService.getCountChapterAllTask(chapter)));

    }

    public AppCompatTextView getChapterCountDone() {
        return chapterCountDone;
    }

    public void notifyCompletedTask(Chapter chapter) {
        chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
                chapterService.getCountChapterDoneTask(chapter),
                chapterService.getCountChapterAllTask(chapter)));
    }
}