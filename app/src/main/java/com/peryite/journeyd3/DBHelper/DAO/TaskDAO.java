package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM TASK")
    List<TaskEntity> getAllEntity();

    @Insert
    long insert(TaskEntity taskEntity);
}
