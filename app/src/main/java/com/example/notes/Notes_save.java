package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class Notes_save extends AppCompatActivity {

    EditText note;
    HashSet<String> set;

    public void change() {
        MainActivity.note.add(note.getText().toString());
        MainActivity.arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_save);
        this.setTitle("Your Notes");

        note = (EditText) findViewById(R.id.editText);
        final int id = getIntent().getIntExtra("position", -1);


        if (id != -1) {
            note.setText(MainActivity.note.get(id));
        }

        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /*
            Things done when the data is changed in the edit text
            ie. if the notes are changed.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.note.set(MainActivity.note.size() - 1, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences preferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                set = new HashSet<>(MainActivity.note);
                preferences.edit().putStringSet("data", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    /*
    checks if the note list is empty if empty deletes the creates the deleted add list
     */
    @Override
    public void onBackPressed() {

        if (MainActivity.note.get(MainActivity.note.size() - 1).isEmpty()) {
            MainActivity.note.remove(MainActivity.note.size() - 1);
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        super.onBackPressed();
    }
}
