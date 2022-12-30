package com.ahmedsalah.wagabat.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ahmedsalah.wagabat.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertAll(User... users);
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
    @Query("SELECT * FROM user WHERE email IN(:userEmail)")
    User getUserByEmaiL(String userEmail);
}
