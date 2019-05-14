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

import java.io.Serializable;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper implements Serializable {

    public final static String NAME = "dbHelper";

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "journeyDB";
    private final static String TABLE_CHAPTER = "chapter";
    private final static String TABLE_CHAPTERTASK = "chapterTask";

    private final static String CHAPTER_ID = "_id";
    private final static String CHAPTER_NAME = "name";

    private final static String CHAPTERTASK_ID = "_id";
    private final static String CHAPTERTASK_NAME = "name";
    // 0 - not done
    // 1 - done
    private final static String CHAPTERTASK_DONE = "done";
    private final static String CHAPTERTASK_CHAPTER_ID = "chapter_id";

    private final static String CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER
            + "(" + CHAPTER_ID + " INTEGER PRIMARY KEY,"
            + CHAPTER_NAME + " TEXT" + ")";


    private final static String CREATE_TABLE_CHAPTERTASK = "CREATE TABLE " + TABLE_CHAPTERTASK
            + "(" + CHAPTERTASK_ID + " INTEGER PRIMARY KEY,"
            + CHAPTERTASK_NAME + " TEXT,"
            + CHAPTERTASK_DONE + " INTEGER,"
            + CHAPTERTASK_CHAPTER_ID + " INTEGER,"
            + "FOREIGN KEY(" + CHAPTERTASK_CHAPTER_ID + ") REFERENCES " + TABLE_CHAPTER + "(" + CHAPTER_ID + ")" + ")";

    private final static String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAPTER);
        db.execSQL(CREATE_TABLE_CHAPTERTASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + TABLE_CHAPTERTASK);
        db.execSQL(DROP_TABLE + TABLE_CHAPTER);

        onCreate(db);
    }

    public int insertChapter(String chapterName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAPTER_NAME, chapterName);
        int id = (int) database.insert(TABLE_CHAPTER, null, contentValues);
        contentValues.clear();
        database.close();
        return id;
    }

    public int insertChapterTask(String chapterTaskName, int chapterId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAPTERTASK_NAME, chapterTaskName);
        contentValues.put(CHAPTERTASK_DONE, 0);
        contentValues.put(CHAPTERTASK_CHAPTER_ID, chapterId);
        int id = (int) database.insert(TABLE_CHAPTERTASK, null, contentValues);
        contentValues.clear();
        database.close();
        return id;
    }

    public void fillDatabase(ArrayList<Chapter> chapters) {
//        String[] content;
//        for (int i = 0; i < chapters.length; i++) {
//            content = chapters[i].split("\n");
//            chapterId = insertChapter(content[0]);
//            for (int j = 1; j < content.length; j++) {
//                insertChapterTask(content[j], chapterId);
//            }
//        }
        for (int i = 0; i < chapters.size(); i++) {
            chapters.get(i).setId(insertChapter(chapters.get(i).getName()));
            for(ChapterTask task: chapters.get(i).getTasks()){
                insertChapterTask(task.getName(), chapters.get(i).getId());
            }
        }

    }

    public void clearAllTables() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(TABLE_CHAPTERTASK, null, null);
            database.delete(TABLE_CHAPTER, null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        database.close();
    }

    private ArrayList<Chapter> selectAllChapter() {
        ArrayList<Chapter> chapters = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_CHAPTER, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(CHAPTER_ID);
            int nameIndex = cursor.getColumnIndex(CHAPTER_NAME);
            do {
                Chapter chapter = new Chapter();
                chapter.setId(cursor.getInt(idIndex));
                chapter.setName(cursor.getString(nameIndex));
                chapters.add(chapter);
            } while (cursor.moveToNext());
        } else {
            Log.d(LogTag.CHAPTER_TABLE, "0 rows");
        }
//        LogTag.logCursor(cursor);
        cursor.close();
        database.close();
        return chapters;
    }


    private ArrayList<ChapterTask> selectChapterTaskByChapterId(int id) {
        ArrayList<ChapterTask> chapterTasks = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {CHAPTERTASK_ID, CHAPTERTASK_NAME, CHAPTERTASK_DONE};
        String selection = CHAPTERTASK_CHAPTER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TABLE_CHAPTERTASK, columns, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(CHAPTERTASK_ID);
            int nameIndex = cursor.getColumnIndex(CHAPTERTASK_NAME);
            int doneIndex = cursor.getColumnIndex(CHAPTERTASK_DONE);
            do {
                ChapterTask chapterTask = new ChapterTask();
                chapterTask.setId(cursor.getInt(idIndex));
                chapterTask.setName(cursor.getString(nameIndex));
                if (cursor.getInt(doneIndex) == 1) {
                    chapterTask.setDone(true);
                } else {
                    chapterTask.setDone(false);
                }
                chapterTasks.add(chapterTask);
            } while (cursor.moveToNext());
        } else {
            Log.d(LogTag.CHAPTER_TASK_TABLE, "0 rows");
        }
//        LogTag.logCursor(cursor);
        cursor.close();
        database.close();
        return chapterTasks;
    }

    public ArrayList<Chapter> getAllChapters() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Chapter> chapters = selectAllChapter();
        for (Chapter chapter : chapters) {
            chapter.setTasks(selectChapterTaskByChapterId(chapter.getId()));
        }
        database.close();
        return chapters;
    }


    private int updateChapterTask(ChapterTask chapterTask) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String selection = CHAPTERTASK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(chapterTask.getId())};
        if (chapterTask.isDone()) {
            contentValues.put(CHAPTERTASK_DONE, 1);
        } else {
            contentValues.put(CHAPTERTASK_DONE, 0);
        }
       return database.update(TABLE_CHAPTERTASK, contentValues, selection, selectionArgs);
    }

    public void updateChapter(ArrayList<Chapter> chapterList) {
        int updateRowCount = 0;
        for (Chapter chapter : chapterList) {
            for (ChapterTask chapterTask : chapter.getTasks()) {
              updateRowCount += updateChapterTask(chapterTask);
            }
        }
        Log.d(LogTag.RESULT, "updates row count " + updateRowCount);
    }
}
