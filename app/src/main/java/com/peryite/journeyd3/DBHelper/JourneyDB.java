package com.peryite.journeyd3.DBHelper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.entities.ChapterEntity;

@Database(entities = ChapterEntity.class, exportSchema = false, version = 2)
public abstract class JourneyDB extends RoomDatabase {
    private final static String DATABASE_NAME = "journeyDB";
    private static JourneyDB instance;

    public static synchronized JourneyDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), JourneyDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ChapterDAO chapterDAO();
}
