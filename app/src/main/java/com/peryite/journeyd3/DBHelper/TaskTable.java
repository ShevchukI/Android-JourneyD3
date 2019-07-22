package com.peryite.journeyd3.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;
import com.peryite.journeyd3.models.Task;
import com.peryite.journeyd3.utils.LogTag;

import java.util.ArrayList;

class TaskTable implements TableRepository {

    public final static String TABLE_NAME = "task";

    public final static String ID = "_id";
    public final static String NAME = "name";

    // 0 - not done
    // 1 - done
    public final static String DONE = "done";
    public final static String CHAPTER_ID = "chapterId";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + DONE + " INTEGER DEFAULT 0,"
            + CHAPTER_ID + " INTEGER,"
            + "FOREIGN KEY(" + CHAPTER_ID + ") REFERENCES " + ChapterTable.TABLE_NAME + "(" + ChapterTable.ID + ")"
            + ")";

    private final static String DELETE_TABLE = "DELETE FROM " + TABLE_NAME;
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final static String RESET_TASK_TABLE = "UPDATE " + TABLE_NAME + " SET " + DONE + " = 0";

   public int updateTask(SQLiteDatabase database, Task chapterTask) {
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


    public int insertObject(SQLiteDatabase database, Task task, int chapterId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, task.getName());
        if (task.isDone()) {
            contentValues.put(DONE, 1);
        } else {
            contentValues.put(DONE, 0);
        }
        contentValues.put(CHAPTER_ID, chapterId);
        int id = (int) database.insert(TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList<Task> selectTaskByChapterId(SQLiteDatabase database, int id) {
        ArrayList<Task> chapterTasks = new ArrayList<>();
        String[] columns = {ID, NAME, DONE};
        String selection = CHAPTER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int doneIndex = cursor.getColumnIndex(DONE);
            do {
                Task chapterTask = new Task();
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
        cursor.close();
        return chapterTasks;
    }

    public void resetAllTasks(SQLiteDatabase database){
       database.execSQL(RESET_TASK_TABLE);
    }

    @Override
    public void create(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void delete(SQLiteDatabase database) {
        database.execSQL(DELETE_TABLE);
    }

    @Override
    public void drop(SQLiteDatabase database) {
        database.execSQL(DROP_TABLE);
    }

    @Override
    public int getCountRecords(SQLiteDatabase database) {
        return (int) DatabaseUtils.queryNumEntries(database, TABLE_NAME);
    }
}
