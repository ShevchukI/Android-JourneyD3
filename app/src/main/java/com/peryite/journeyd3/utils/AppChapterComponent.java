package com.peryite.journeyd3.utils;

import android.app.Application;

import com.peryite.journeyd3.components.ChapterComponent;
import com.peryite.journeyd3.components.DaggerChapterComponent;

public class AppChapterComponent extends Application {
    private static ChapterComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerChapterComponent.create();
    }

    public static ChapterComponent getComponent() {
        return component;
    }
}
