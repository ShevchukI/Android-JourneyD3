package com.peryite.journeyd3.DBHelper;

import android.database.sqlite.SQLiteDatabase;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;

class RewardTable implements TableRepository {

    public final static String TABLE_NAME = "reward";

    public final static String ID = "_id";
    public final static String NAME = "name";
    // 0 - not done
    // 1 - done
    public final static String DONE = "done";

    private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + DONE + " INTEGER DEFAULT 0"
            + ")";

    private final static String DELETE_TABLE = "DELETE FROM " + TABLE_NAME;
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

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
}
