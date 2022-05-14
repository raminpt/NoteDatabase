package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.NoteStore;
import com.example.myapplication.dao.NoteStoreAbs;
import com.example.myapplication.database.NoteDatabase;
import com.example.myapplication.entity.HobbyUser;
import com.example.myapplication.entity.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    Bundle bundle;
    User user;
    TextView usernameText, userHobbies1, userHobbies2, userHobbies3;
    NoteDatabase noteDatabase;
    NoteStoreAbs noteStoreAbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bundle = getIntent().getExtras();
        user = bundle.getParcelable("user");

        init();
        usernameText.setText(user.getUsername());
        loadAllHobbiesByUser(user);
    }

    public void init() {
        usernameText = findViewById(R.id.text_profile_username);
        userHobbies1 = findViewById(R.id.text_hobbies_1);
        userHobbies2 = findViewById(R.id.text_hobbies_2);
        userHobbies3 = findViewById(R.id.text_hobbies_3);
        noteDatabase = NoteDatabase.getNoteDatabase(this);
        noteStoreAbs = noteDatabase.noteStoreAbs();
    }

    public void loadAllHobbiesByUser(User user) {
        Single<HobbyUser> hobbyUserSingle = noteStoreAbs.getAllHobbiesByUserId(user.getId());
        hobbyUserSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hobbyUser -> {
                    userHobbies1.setText(hobbyUser.hobbyList.get(0).getInterest());
                    userHobbies2.setText(hobbyUser.hobbyList.get(1).getInterest());
                    userHobbies3.setText(hobbyUser.hobbyList.get(2).getInterest());
                });
    }
}