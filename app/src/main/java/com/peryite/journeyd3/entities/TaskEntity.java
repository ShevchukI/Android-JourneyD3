package com.peryite.journeyd3.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "task",
        foreignKeys = @ForeignKey(entity = ChapterEntity.class, parentColumns = "_id", childColumns = "chapter_id", onDelete = CASCADE),
        indices = @Index(value = "chapter_id"))
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "complete")
    private boolean complete;
    @ColumnInfo(name = "chapter_id")
    private long chapterId;


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

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }
}
