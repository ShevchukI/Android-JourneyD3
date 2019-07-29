package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.models.Chapter;

import java.util.List;

@Dao
public interface ChapterDAO {
    @Query("SELECT _id, name FROM CHAPTER")
    List<Chapter> getAll();

    @Insert
    void insert(ChapterEntity chapterEntity);
}
