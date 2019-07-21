package com.peryite.journeyd3.utils;

import android.app.Application;

import com.peryite.journeyd3.components.ChapterComponent;
import com.peryite.journeyd3.components.DaggerChapterComponent;
//import com.peryite.journeyd3.components.D
import com.peryite.journeyd3.components.DaggerFragmentComponent;
import com.peryite.journeyd3.components.FragmentComponent;

public class AppAllComponent extends Application {
    private static ChapterComponent chapterComponent;
    private static FragmentComponent fragmentComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        chapterComponent = DaggerChapterComponent.create();
        fragmentComponent = DaggerFragmentComponent.create();
    }

    public static ChapterComponent getChapterComponent() {
        return chapterComponent;
    }

    public static FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}
