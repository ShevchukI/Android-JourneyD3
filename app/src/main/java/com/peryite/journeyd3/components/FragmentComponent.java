package com.peryite.journeyd3.components;

import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.modules.FragmentModule;

import dagger.Component;

@Component(modules = FragmentModule.class)
public interface FragmentComponent {
    void injectsFragments(FragmentManager fragmentManager);
}
