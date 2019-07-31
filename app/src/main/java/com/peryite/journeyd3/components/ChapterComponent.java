package com.peryite.journeyd3.components;

import com.peryite.journeyd3.modules.ChapterModule;
import com.peryite.journeyd3.viewHolders.parent.ChapterViewHolder;

import dagger.Component;

@Component(modules = ChapterModule.class)
public interface ChapterComponent {
    void injectsChapterService(ChapterViewHolder chapterViewHolder);
}
