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
        @BindView(R.id.iv_arrow)
        AppCompatImageView arrowImage;

        private ChapterService chapterService;

        public ChapterViewHolder(@NonNull View itemView, ChapterService chapterService) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.chapterService = chapterService;
//            arrowImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    expandCollaps();
//                }
//            });
        }

        public boolean shouldItemViewClickToggleExpansion() {
            return true;
        }


        public void expandCollaps(){
            if (isExpanded()) {
                collapseView();
                arrowImage.animate().rotation(0);
            } else {
                expandView();
                arrowImage.animate().rotation(180);
            }
        }

        public void rotateArrow(float value){
            arrowImage.animate().rotation(value);
        }

//        public void bind(int parentPosition) {
//            chapterName.setText(chapters.get(parentPosition).getName());
//            chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
//                    chapterService.getCountChapterDoneTask(chapters.get(parentPosition)),
//                    chapterService.getCountChapterAllTask(chapters.get(parentPosition))));
//        }

        public void bind(Chapter chapter) {
            chapterName.setText(chapter.getName());
            chapterCountDone.setText(itemView.getResources().getString(R.string.chapter_complete_vs_all_task,
                    chapterService.getCountChapterDoneTask(chapter),
                    chapterService.getCountChapterAllTask(chapter)));
        }
    }