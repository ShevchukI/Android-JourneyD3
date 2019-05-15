package com.peryite.journeyd3.DBHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.ChapterTask;
import com.peryite.journeyd3.tools.LogTag;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private RewardTable rewardTable;
    private ChapterTable chapterTable;
    private ChapterTaskTable chapterTaskTable;

    // database settings
    public final static String NAME = "dbHelper";
    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "journeyDB";

    // tables name
    private final static String TABLE_CONQUEST = "conquest";
    private final static String TABLE_CONQUEST_MODE = "conquestMode";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        rewardTable = new RewardTable();
        chapterTable = new ChapterTable();
        chapterTaskTable = new ChapterTaskTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(rewardTable.createTable());
        db.execSQL(chapterTable.createTable());
        db.execSQL(chapterTaskTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(chapterTaskTable.dropTable());
        db.execSQL(chapterTable.dropTable());
        db.execSQL(rewardTable.dropTable());

        onCreate(db);
    }

    public void fillDatabase(ArrayList<Chapter> chapters) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (int i = 0; i < chapters.size(); i++) {
            chapters.get(i).setId(chapterTable.insertChapter(database, chapters.get(i).getName()));
            for (int j = 0; j < chapters.get(i).getTasks().size(); j++) {
                ChapterTask chapterTask = new ChapterTask();
                chapterTask.setId(chapters.get(i).getId()*10 + j);
                chapterTask.setName(chapters.get(i).getTasks().get(j).getName());
                chapterTask.setDone(false);
                chapterTaskTable.insertChapterTask(database, chapters.get(i).getId(), chapterTask);
            }
        }
        database.close();
    }

    public void clearAllTables() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(ChapterTaskTable.TABLE_NAME, null, null);
            database.delete(ChapterTable.TABLE_NAME, null, null);
            database.delete(RewardTable.TABLE_NAME, null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        database.close();
    }


    public ArrayList<Chapter> getAllChapters() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Chapter> chapters = chapterTable.selectAllChapter(database);
        for (Chapter chapter : chapters) {
            chapter.setTasks(chapterTaskTable.selectChapterTaskByChapterId(database, chapter.getId()));
        }
        database.close();
        return chapters;
    }

    public int updateChapter(ArrayList<Chapter> chapterList) {
        SQLiteDatabase database = this.getReadableDatabase();
        int updateRowCount = 0;
        for (Chapter chapter : chapterList) {
            for (ChapterTask chapterTask : chapter.getTasks()) {
                updateRowCount += chapterTaskTable.updateChapterTask(database, chapterTask);
            }
        }
        Log.d(LogTag.RESULT, "updates row count " + updateRowCount);
        return updateRowCount;
    }
}
