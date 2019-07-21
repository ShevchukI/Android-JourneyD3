package com.peryite.journeyd3.managers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.peryite.journeyd3.MainActivity;
import com.peryite.journeyd3.fragments.FragmentChapter;
import com.peryite.journeyd3.fragments.FragmentConquest;
import com.peryite.journeyd3.fragments.FragmentCredits;
import com.peryite.journeyd3.fragments.FragmentReward;
import com.peryite.journeyd3.utils.AppAllComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FragmentManager {
    private static final FragmentManager ourInstance = new FragmentManager();

    @Inject
    FragmentChapter fragmentChapter;
    @Inject
    FragmentConquest fragmentConquest;
    @Inject
    FragmentCredits fragmentCredits;
    @Inject
    FragmentReward fragmentReward;

    private List<Fragment> fragments;

    public static FragmentManager getInstance() {
        return ourInstance;
    }

    private FragmentManager() {
    }



    public void initFragments() {
           AppAllComponent.getFragmentComponent().injectsFragments(this);
//            FragmentComponent component = DaggerFragmentComponent.create();
//            component.injectsFragments(activity);
            fragments = fillChapterList();

    }

    private List<Fragment> fillChapterList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragmentChapter);
        fragmentList.add(fragmentConquest);
        fragmentList.add(fragmentCredits);
        fragmentList.add(fragmentReward);
        return fragmentList;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public FragmentChapter getFragmentChapter() {
        return fragmentChapter;
    }

    public FragmentConquest getFragmentConquest() {
        return fragmentConquest;
    }

    public FragmentCredits getFragmentCredits() {
        return fragmentCredits;
    }

    public FragmentReward getFragmentReward() {
        return fragmentReward;
    }
}
