package com.peryite.journeyd3.models;

import java.util.ArrayList;

public class Chapter {
    private int id;
    private String name;
    private ArrayList<Task> tasks;
    private Reward reward;

    public Chapter() {
    }

    public Chapter(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
