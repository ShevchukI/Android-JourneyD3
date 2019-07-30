package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.journeyd3.entities.ChapterEntity;

import java.util.List;

@Dao
public interface ChapterDAO {

    @Query("SELECT _id, name FROM CHAPTER")
    List<ChapterEntity> getAllEntity();

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
