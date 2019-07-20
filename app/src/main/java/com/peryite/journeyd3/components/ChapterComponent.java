package com.peryite.journeyd3.components;

import com.peryite.journeyd3.MainActivity;
import com.peryite.journeyd3.adapters.ChapterRecyclerAdapter;
import com.peryite.journeyd3.fragments.FragmentChapter;
import com.peryite.journeyd3.modules.ChapterModule;

import dagger.Component;

@Component(modules = ChapterModule.class)
public interface ChapterComponent {
    void injectsChapterService(FragmentChapter fragmentChapter);
}
