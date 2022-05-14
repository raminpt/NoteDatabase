package com.example.myapplication.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.NoteStore;
import com.example.myapplication.dao.NoteStoreAbs;
import com.example.myapplication.entity.Hobby;
import com.example.myapplication.entity.HobbyUserPivot;
import com.example.myapplication.entity.Note;
import com.example.myapplication.entity.User;

@Database(entities = {Note.class, User.class, Hobby.class, HobbyUserPivot.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String TAG = "NoteDatabase";

    public abstract NoteStore noteStore();
    public abstract NoteStoreAbs noteStoreAbs();
    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getNoteDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").build();
                    Log.d(TAG, "getNoteDatabase: " + INSTANCE);
                }
            }
        }
        return INSTANCE;
    }
}
