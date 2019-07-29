package com.peryite.journeyd3.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "reward")
public class RewardEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "complete")
    private boolean complete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
