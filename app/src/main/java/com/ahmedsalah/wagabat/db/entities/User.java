package com.ahmedsalah.wagabat.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public String name;
    @ColumnInfo
    public String mobile;
    @ColumnInfo
    public String email;
    @ColumnInfo
    public String address;

    public User(String name, String email, String mobile, String address){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }
}
