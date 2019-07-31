package com.peryite.journeyd3.modules;

import com.peryite.journeyd3.mvp.chapter.view.FragmentChapter;
import com.peryite.journeyd3.fragments.FragmentConquest;
import com.peryite.journeyd3.fragments.FragmentCredits;
import com.peryite.journeyd3.fragments.FragmentReward;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    @Provides
    FragmentChapter provideFragmentChapter(){
        return new FragmentChapter();
    }

    @Provides
    FragmentConquest provideFragmentConquest(){
        return new FragmentConquest();
    }

    @Provides
    FragmentCredits provideFragmentCredits(){
        return new FragmentCredits();
    }

    @Provides
    FragmentReward provideFragmentRevard(){
        return new FragmentReward();
    }
}
