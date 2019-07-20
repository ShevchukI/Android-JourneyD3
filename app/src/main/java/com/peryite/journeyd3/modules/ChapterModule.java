package com.peryite.journeyd3.modules;

import com.peryite.journeyd3.services.ChapterService;

import dagger.Module;
import dagger.Provides;

@Module
public class ChapterModule {

    @Provides
    ChapterService provideChapterService(){
        return new ChapterService();
    }
}
