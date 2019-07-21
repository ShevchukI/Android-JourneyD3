package com.peryite.journeyd3.components;

import android.app.Activity;

import com.peryite.journeyd3.MainActivity;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.modules.FragmentModule;

import dagger.Component;

@Component(modules = FragmentModule.class)
public interface FragmentComponent {
    void injectsFragments(FragmentManager fragmentManager);
//    void injectsFragmentChapter(Activity activity);
//    void injectsFragmentConquest(Activity activity);
//    void injectsFragmentCredits(Activity activity);
//    void injectsFragmentReward(Activity activity);
}
