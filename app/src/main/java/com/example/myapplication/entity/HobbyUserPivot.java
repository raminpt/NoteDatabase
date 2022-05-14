package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "hobby_user",
        primaryKeys = {"userId", "hobbyId"},
        indices = @Index(value = {"userId", "hobbyId"}),
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                        @ForeignKey(entity = Hobby.class, parentColumns = "id", childColumns = "hobbyId", onDelete = ForeignKey.CASCADE)})
public class HobbyUserPivot {
    private long userId;
    private long hobbyId;

    public HobbyUserPivot() {
    }

    @Ignore
    public HobbyUserPivot(long hobbyId) {
        this.hobbyId = hobbyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(long hobbyId) {
        this.hobbyId = hobbyId;
    }
}
