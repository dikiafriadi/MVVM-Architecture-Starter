package com.aditp.mdvkarch.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.aditp.mdvkarch.data.model.ResponseProfileUser;

@Database(entities = {ResponseProfileUser.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract DaoProfileUser userDao();
}