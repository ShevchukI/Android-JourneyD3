package com.peryite.journeyd3.DBHelper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.DBHelper.DAO.TaskDAO;
import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;
import com.peryite.journeyd3.utils.Constant;

@Database(entities = {ChapterEntity.class, TaskEntity.class},
        exportSchema = false, version = Constant.DATABASE_VERSION)
public abstract class JourneyDB extends RoomDatabase {
    private static JourneyDB instance;

    public static synchronized JourneyDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), JourneyDB.class, Constant.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ChapterDAO chapterDAO();

    public abstract TaskDAO taskDAO();
}
