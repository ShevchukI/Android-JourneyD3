package com.peryite.journeyd3.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Reward;
import com.peryite.journeyd3.utils.LogTag;

import java.util.ArrayList;

class ChapterTable implements TableRepository {

    public static String TABLE_NAME = "chapter";

    public final static String NAME = "name";
    public final static String ID = "_id";
    public final static String REWARD_ID = "rewardId";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + REWARD_ID + " INTEGER,"
            + "FOREIGN KEY(" + REWARD_ID + ") REFERENCES " + RewardTable.TABLE_NAME + "(" + RewardTable.ID + ")"
            + ")";

    private final static String DELETE_TABLE = "DELETE FROM " + TABLE_NAME;
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final static String COUNT_RECORDS = "SELECT COUNT(*) FROM " + TABLE_NAME;


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

    public int insertObject(SQLiteDatabase database, Chapter chapter) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, chapter.getName());
        contentValues.put(REWARD_ID, chapter.getReward().getId());
        int id = (int) database.insert(TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList<Chapter> selectAllChapter(SQLiteDatabase database) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int rewardIndex = cursor.getColumnIndex(REWARD_ID);
            do {
                Chapter chapter = new Chapter();
                chapter.setId(cursor.getInt(idIndex));
                chapter.setName(cursor.getString(nameIndex));
                chapter.setReward(new Reward(cursor.getInt(rewardIndex)));
                chapters.add(chapter);
            } while (cursor.moveToNext());
        } else {
            Log.d(LogTag.CHAPTER_TABLE, "0 rows");
        }
        cursor.close();
        return chapters;
    }
}
