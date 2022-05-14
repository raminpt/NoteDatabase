package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.NoteStore;
import com.example.myapplication.database.NoteDatabase;
import com.example.myapplication.entity.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "database";

    Button loginButton;
    EditText usernameEditText, passwordEditText;
    NoteDatabase noteDatabase;
    NoteStore noteStore;
    TextView createUserText;
    BottomSheetFragment bottomSheetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            login(username);
        });

        createUserText.setOnClickListener(v -> {
            bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });
    }

    public void init() {
        noteDatabase = NoteDatabase.getNoteDatabase(this);
        Log.d(TAG, "database instance in MainActivity" + noteDatabase);
        noteStore = noteDatabase.noteStore();
        loginButton = findViewById(R.id.login_button);
        usernameEditText = findViewById(R.id.edit_text_username);
        passwordEditText = findViewById(R.id.edit_text_password);
        createUserText = findViewById(R.id.text_create_user);
    }

    public void login(String username) {
        Single<User> userSingle = noteStore.loadUser(username);
        userSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::checkUser, this::handleError);
    }

    public void checkUser(User user) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals(user.getUsername())) {
            if(password.equals(user.getPassword())) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "password incorrect", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, user.getUsername() + " does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleError(Throwable throwable) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        System.out.println("error login: " + throwable.getMessage());
    }
}