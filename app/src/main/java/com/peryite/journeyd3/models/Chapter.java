package com.peryite.journeyd3.models;


import java.util.ArrayList;

public class Chapter {

    public final static String TOKEN = "chapterToken";

    private int id;
    private String name;
    private ArrayList<ChapterTask> tasks;
    private Reward reward;

    public Chapter() {
    }

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
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

    public ArrayList<ChapterTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ChapterTask> tasks) {
        this.tasks = tasks;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
