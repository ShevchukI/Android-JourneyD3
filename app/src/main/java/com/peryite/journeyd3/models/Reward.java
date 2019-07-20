package com.peryite.journeyd3.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Reward {
    private int id;
    private String name;
    private boolean done;

    public Reward(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public Reward(int id) {
        this.id = id;
    }
}
