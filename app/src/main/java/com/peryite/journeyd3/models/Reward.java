package com.peryite.journeyd3.models;

public class Reward {
    private int id;
    private String name;
    private boolean done;

    public Reward() {
    }

    public Reward(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reward(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public Reward(int id) {
        this.id = id;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
