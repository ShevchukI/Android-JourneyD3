package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.models.Chapter;

import java.util.List;

@Dao
public interface ChapterDAO {
    @Query("SELECT * FROM CHAPTER")
    List<Chapter> getAll();

    @Query("SELECT _id, name FROM CHAPTER")
    List<ChapterEntity> getAllEntity();

    @Query("SELECT _id, name FROM CHAPTER WHERE _id = :id")
    Chapter getById(long id);

    @Insert
    long insert(ChapterEntity chapterEntity);

    @Insert
    long[] insert(List<ChapterEntity> chapterEntities);

    @Update
    int update(ChapterEntity chapterEntity);

    @Delete
    int delete(ChapterEntity chapterEntity);

    @Query("DELETE FROM CHAPTER")
    void deleteAll();
}
