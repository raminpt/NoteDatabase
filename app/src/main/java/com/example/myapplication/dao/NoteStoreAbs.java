package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myapplication.entity.Hobby;
import com.example.myapplication.entity.HobbyUser;
import com.example.myapplication.entity.HobbyUserPivot;
import com.example.myapplication.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class NoteStoreAbs {

    @Insert
    public abstract long insertUser(User user);

    @Insert
    public abstract void insertPivot(List<HobbyUserPivot> pivotList);

    @Transaction
    public void insert(User user, List<HobbyUserPivot> pivotList) {
        long result = insertUser(user);
        for (int i = 0; i < pivotList.size(); i++) {
            pivotList.get(i).setUserId(result);
        }
        insertPivot(pivotList);
    }

    @Query("SELECT * FROM hobbies")
    public abstract Single<List<Hobby>> getAllHobbies();

    @Query("SELECT * FROM users WHERE id = :id")
    public abstract Single<HobbyUser> getAllHobbiesByUserId(long id);
}
