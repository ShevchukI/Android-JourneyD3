package com.peryite.journeyd3.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;
import com.peryite.journeyd3.models.ChapterTask;
import com.peryite.journeyd3.tools.LogTag;

import java.util.ArrayList;

class ChapterTaskTable implements TableRepository {
    public final static String TABLE_NAME = "chapterTask";
    public final static String ID = "_id";
    public final static String NAME = "name";
    // 0 - not done
    // 1 - done
    public final static String DONE = "done";
    public final static String CHAPTER_ID = "chapter_id";
    private final static String CREATE_TABLE_CHAPTERTASK = "CREATE TABLE " + TABLE_NAME
            + "(" + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + DONE + " INTEGER,"
            + CHAPTER_ID + " INTEGER,"
            + "FOREIGN KEY(" + CHAPTER_ID + ") REFERENCES " + ChapterTable.TABLE_NAME + "(" + ChapterTable.ID + ")"
            + ")";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS ";


    @Override
    public String createTable() {
        return CREATE_TABLE_CHAPTERTASK;
    }

    @Override
    public String dropTable() {
        return DROP_TABLE + TABLE_NAME;
    }

    public int insertChapterTask(SQLiteDatabase database, int chapterId, ChapterTask chapterTask) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(ID, chapterTask.getId());
        contentValues.put(NAME, chapterTask.getName());
        if(chapterTask.isDone()){
            contentValues.put(DONE, 1);
        } else {
            contentValues.put(DONE, 0);
        }
        contentValues.put(CHAPTER_ID, chapterId);
        int id = (int) database.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        return id;
    }

    public ArrayList<ChapterTask> selectChapterTaskByChapterId(SQLiteDatabase database, int id) {
        ArrayList<ChapterTask> chapterTasks = new ArrayList<>();
        String[] columns = {ID, NAME, DONE};
        String selection = CHAPTER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int doneIndex = cursor.getColumnIndex(DONE);
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
        return chapterTasks;
    }

    public int updateChapterTask(SQLiteDatabase database, ChapterTask chapterTask) {
        ContentValues contentValues = new ContentValues();
        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(chapterTask.getId())};
        if (chapterTask.isDone()) {
            contentValues.put(DONE, 1);
        } else {
            contentValues.put(DONE, 0);
        }
        return database.update(TABLE_NAME, contentValues, selection, selectionArgs);
    }

}
