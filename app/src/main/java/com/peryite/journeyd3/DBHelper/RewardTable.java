package com.peryite.journeyd3.DBHelper;

import com.peryite.journeyd3.DBHelper.DAO.TableRepository;

class RewardTable implements TableRepository {
    public final static String TABLE_NAME = "reward";

    public final static String ID = "_id";
    public final static String NAME = "name";
    // 0 - not done
    // 1 - done
    public final static String DONE = "done";
    private final static String CREATE_TABLE_REWARD = "CREATE TABLE " + TABLE_NAME
            + "(" + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + DONE + " INTEGER"
            + ")";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS ";


    @Override
    public String createTable() {
        return CREATE_TABLE_REWARD;
    }

    @Override
    public String dropTable() {
        return DROP_TABLE + TABLE_NAME;
    }
}
