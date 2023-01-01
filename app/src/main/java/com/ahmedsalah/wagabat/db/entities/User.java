package com.ahmedsalah.wagabat.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo
    public String authid;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String mobile;
    @ColumnInfo
    public String email;

    public User(String authid, String name, String email, String mobile){
        this.authid = authid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}
