package com.mz.befclient.data;

import com.mz.befclient.basket.FatoraDetail;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FatoraDetail.class},version = 12)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract Dao getDao();
}
