package com.peryite.journeyd3.components;

import com.peryite.journeyd3.mvp.chapter.view.FragmentChapter;
import com.peryite.journeyd3.modules.ChapterModule;

import dagger.Component;

@Component(modules = ChapterModule.class)
public interface ChapterComponent {
    void injectsChapterService(FragmentChapter fragmentChapter);
}
