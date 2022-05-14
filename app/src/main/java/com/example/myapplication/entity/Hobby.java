package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hobbies")
public class Hobby {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String interest;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
