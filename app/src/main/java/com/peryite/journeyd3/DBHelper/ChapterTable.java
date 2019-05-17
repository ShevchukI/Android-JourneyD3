package com.peryite.journeyd3.DBHelper;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;

class ChapterTable implements TableRepository {

    public final static String TABLE_NAME = "chapter";

    public final static String ID = "_id";
    public final static String NAME = "name";
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
}
