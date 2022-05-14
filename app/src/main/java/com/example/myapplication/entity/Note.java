package com.example.myapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes", foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id", onDelete = ForeignKey.CASCADE)})
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String noteText;
    @ColumnInfo(name = "user_id", index = true)
    private long userId;

    public Note(String noteText, long userId) {
        this.noteText = noteText;
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getNoteText() {
        return noteText;
    }

    public long getUserId() {
        return userId;
    }
}
