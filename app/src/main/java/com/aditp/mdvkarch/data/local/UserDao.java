package com.aditp.mdvkarch.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void save(User user);

    @Query("SELECT * FROM user WHERE login = :userLogin")
    LiveData<User> load(String userLogin);

    @Query("SELECT * FROM user WHERE login = :userLogin AND lastRefresh > :lastRefreshMax LIMIT 1")
    User hasUser(String userLogin, Date lastRefreshMax);
}