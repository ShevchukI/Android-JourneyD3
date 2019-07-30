package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM TASK")
    List<TaskEntity> getAllEntity();

    @Query("SELECT * FROM TASK WHERE chapter_id = :chapterId")
    List<TaskEntity> getAllEntityByChapterId(long chapterId);

    @Query("UPDATE TASK SET complete = 0")
    void reset();

    @Insert
    long insert(TaskEntity taskEntity);

    @Update
    int update(TaskEntity taskEntity);
}
