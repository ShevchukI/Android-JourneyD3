package com.peryite.journeyd3.models;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class Chapter implements Parent<Task> {
    private int id;
    @NonNull
    private String name;
    private List<Task> tasks;
    private Reward reward;

    public Chapter(String name, List<Task> tasks){
        this.tasks = tasks;
    }


    @Override
    public List<Task> getChildList() {
        return tasks;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
