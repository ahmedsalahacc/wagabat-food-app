package com.ahmedsalah.wagabat.db.databases;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.ahmedsalah.wagabat.db.dao.UserDao;
import com.ahmedsalah.wagabat.db.entities.User;

@Database(entities = {User.class}, version=1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
