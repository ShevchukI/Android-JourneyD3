package com.peryite.journeyd3.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;
import com.peryite.journeyd3.utils.LogTag;

import java.util.ArrayList;

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

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        conquestTable.delete(db);
        taskTable.delete(db);
        chapterTable.delete(db);
        rewardTable.delete(db);
        db.close();
    }

    public boolean checkRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (conquestTable.getCountRecords(db) != 0) {
            return true;
        } else if (taskTable.getCountRecords(db) != 0) {
            return true;
        } else if (chapterTable.getCountRecords(db) != 0) {
            return true;
        } else if (rewardTable.getCountRecords(db) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void fillDatabase(ArrayList<Chapter> chapterArrayList) {
        SQLiteDatabase database = this.getWritableDatabase();
        for(Chapter chapter: chapterArrayList){
            chapter.getReward().setId(rewardTable.insertObject(database, chapter.getReward()));
            chapter.setId(chapterTable.insertObject(database, chapter));
            for(Task task: chapter.getTasks()){
                taskTable.insertObject(database, task, chapter.getId());
            }
        }
    }

    public ArrayList<Chapter> getAllChapters() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Chapter> chapters = chapterTable.selectAllChapter(database);
        for (Chapter chapter : chapters) {
            chapter.setTasks(taskTable.selectTaskByChapterId(database, chapter.getId()));
        }
        database.close();
        return chapters;
    }

    public int updateChapter(ArrayList<Chapter> chapterList) {
        SQLiteDatabase database = this.getReadableDatabase();
        int updateRowCount = 0;
        for (Chapter chapter : chapterList) {
            for (Task chapterTask : chapter.getTasks()) {
                updateRowCount += taskTable.updateTask(database, chapterTask);
            }
        }
        Log.d(LogTag.RESULT, "updates row count " + updateRowCount);
        return updateRowCount;
    }
}
