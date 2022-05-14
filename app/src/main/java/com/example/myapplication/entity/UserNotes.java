package com.example.myapplication.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserNotes {
    @Embedded
    public User user;
    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Note> noteList;
}
