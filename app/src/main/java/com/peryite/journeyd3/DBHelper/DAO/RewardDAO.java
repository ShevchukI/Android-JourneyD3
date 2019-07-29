package com.peryite.journeyd3.DBHelper.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.journeyd3.entities.RewardEntity;
import com.peryite.journeyd3.models.Reward;

import java.util.List;

@Dao
public interface RewardDAO {
    @Query("SELECT * FROM REWARD")
    List<Reward> getAll();

    @Query("SELECT * FROM REWARD WHERE _id = :id")
    Reward getById(long id);

    @Insert
    long insert(RewardEntity rewardEntity);

    @Update
    int update(RewardEntity rewardEntity);

    @Delete
    int delete(RewardEntity rewardEntity);
}
