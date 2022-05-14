package com.example.myapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Insert;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.NoteStore;
import com.example.myapplication.dao.NoteStoreAbs;
import com.example.myapplication.database.NoteDatabase;
import com.example.myapplication.entity.Hobby;
import com.example.myapplication.entity.HobbyUserPivot;
import com.example.myapplication.entity.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final String TAG = "fragment";

    NoteDatabase noteDatabase;
    NoteStoreAbs noteStoreAbs;
    NoteStore noteStore;
    EditText usernameEditText, passwordEditText;
    Button createButton;
    Spinner spinner1, spinner2, spinner3;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteDatabase = NoteDatabase.getNoteDatabase(getContext());
        Log.d(TAG, "database instance in fragment" + noteDatabase);
        noteStore = noteDatabase.noteStore();
        noteStoreAbs = noteDatabase.noteStoreAbs();
        loadHobbies();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEditText = view.findViewById(R.id.edit_text_create_username);
        passwordEditText = view.findViewById(R.id.edit_text_create_password);
        createButton = view.findViewById(R.id.create_button);
        spinner1 = view.findViewById(R.id.hobby_spinner_1);
        spinner2 = view.findViewById(R.id.hobby_spinner_2);
        spinner3 = view.findViewById(R.id.hobby_spinner_3);



        createButton.setOnClickListener(v -> {
            User user = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            List<HobbyUserPivot> hobbyList = new ArrayList<>();
            HobbyUserPivot hobbyUserPivot1, hobbyUserPivot2, hobbyUserPivot3;
            hobbyUserPivot1 = new HobbyUserPivot(spinner1.getSelectedItemPosition() + 1);
            hobbyUserPivot2 = new HobbyUserPivot(spinner2.getSelectedItemPosition() + 1);
            hobbyUserPivot3 = new HobbyUserPivot(spinner3.getSelectedItemPosition() + 1);
            hobbyList.add(hobbyUserPivot1);
            hobbyList.add(hobbyUserPivot2);
            hobbyList.add(hobbyUserPivot3);
            createUser(user, hobbyList);
        });
    }

    public void createUser(User user, List<HobbyUserPivot> pivotList) {

        Completable insertUser = Completable.fromRunnable(() -> noteStoreAbs.insert(user, pivotList));
        insertUser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Toast.makeText(getContext(), user.getUsername() + " created", Toast.LENGTH_SHORT).show(),
                        throwable -> {Toast.makeText(getContext(), user.getUsername() + " created", Toast.LENGTH_SHORT).show();
                        System.out.println("error on create user " + throwable.getMessage());
                });

//        noteDatabase.runInTransaction(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    noteStoreAbs.insert(user, pivotList);
//                    Toast.makeText(getContext(), user.getUsername() + " created", Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e) {
//                    Toast.makeText(getContext(), "error on create user", Toast.LENGTH_SHORT).show();
//                    System.out.println("error on create user " + e.getMessage());
//                }
//
//            }
//        });
    }


    public void loadHobbies() {
        Single<List<Hobby>> listHobbySingle = noteStoreAbs.getAllHobbies();
        listHobbySingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hobbies -> {
                    List<String> hobbyList = new ArrayList<>();
                    for (int i = 0; i < hobbies.size(); i++) {
                        hobbyList.add(hobbies.get(i).getInterest());
                    }

                    ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, hobbyList);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(adapter1);

                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, hobbyList);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);

                    ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, hobbyList);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter3);
                });
    }
}