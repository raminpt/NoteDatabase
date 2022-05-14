package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;

import com.example.myapplication.entity.Hobby;
import com.example.myapplication.entity.HobbyUser;
import com.example.myapplication.entity.HobbyUserPivot;
import com.example.myapplication.entity.Note;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserNotes;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteStore {

    @Insert
    Completable insertNote(Note note);

    @Query("SELECT * FROM users WHERE username = :username")
    Single<User> loadUser(String username);

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    Single<UserNotes> loadNotes(long userId);
}
