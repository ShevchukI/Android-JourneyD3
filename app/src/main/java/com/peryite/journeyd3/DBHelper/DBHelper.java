package com.peryite.journeyd3.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // database parameter
    private final static String DATABASE_NAME = "journeyDB";
    private final static int DATABASE_VERSION = 1;

    // tables in database
    private RewardTable rewardTable;
    private ChapterTable chapterTable;
    private TaskTable taskTable;
    private ConquestTable conquestTable;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        rewardTable = new RewardTable();
        chapterTable = new ChapterTable();
        taskTable = new TaskTable();
        conquestTable = new ConquestTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        rewardTable.create(db);
        chapterTable.create(db);
        taskTable.create(db);
        conquestTable.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        conquestTable.drop(db);
        taskTable.drop(db);
        chapterTable.drop(db);
        rewardTable.drop(db);

        onCreate(db);
    }

    public void deleteAllRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        conquestTable.delete(db);
        taskTable.delete(db);
        chapterTable.delete(db);
        rewardTable.delete(db);
        db.close();
    }
}
