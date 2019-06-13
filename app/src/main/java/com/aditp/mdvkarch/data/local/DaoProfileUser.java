package com.aditp.mdvkarch.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aditp.mdvkarch.data.model.ResponseProfileUser;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface DaoProfileUser {

    @Insert(onConflict = REPLACE)
    void save(ResponseProfileUser user);

    @Query("DELETE FROM ResponseProfileUser")
    void deleteAllData();

    @Query("SELECT * FROM ResponseProfileUser ORDER BY login DESC")
    LiveData<List<ResponseProfileUser>> getAllData();
}