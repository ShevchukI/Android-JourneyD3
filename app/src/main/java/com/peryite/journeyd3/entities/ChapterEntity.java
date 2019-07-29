package com.peryite.journeyd3.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "chapter")
public class ChapterEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "rewardId")
    private long rewardId;

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

    public long getRewardId() {
        return rewardId;
    }

    public void setRewardId(long rewardId) {
        this.rewardId = rewardId;
    }
}
