package com.peryite.journeyd3.DBHelper.DAO;

import android.database.sqlite.SQLiteDatabase;

public interface TableRepository {
    void create(SQLiteDatabase database);

    void delete(SQLiteDatabase database);

    void drop(SQLiteDatabase database);

    long getCountRecords(SQLiteDatabase database);
}
