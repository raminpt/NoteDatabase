package com.example.myapplication.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class HobbyUser {
    // Add the @Relation annotation to the instance of the child entity, with parentColumn set to
    // the name of the primary key column of the parent entity and entityColumn set to the name of
    // the column of the child entity that references the parent entity's primary key.
    @Embedded
    public User user;
    @Relation(parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(value = HobbyUserPivot.class, parentColumn = "userId", entityColumn = "hobbyId"))
    public List<Hobby> hobbyList;
}
