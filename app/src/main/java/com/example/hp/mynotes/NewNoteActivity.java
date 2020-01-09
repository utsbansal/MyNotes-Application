package com.example.hp.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends AppCompatActivity {

    EditText title, note;
    Button Save, Discard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = (EditText)findViewById(R.id.editText);
        note = (EditText)findViewById(R.id.editText2);
        Save = (Button)findViewById(R.id.save_bt);
        Discard = (Button)findViewById(R.id.discard_bt);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TitleStr=title.getText().toString();
                final String NoteStr=note.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COL_TITLE, TitleStr);
                contentValues.put(DatabaseHelper.COL_NOTE, NoteStr);

                DatabaseHelper  mysqliteopenhelper = new DatabaseHelper(getApplicationContext());
                boolean flag=mysqliteopenhelper.insertData(contentValues);
                if(flag)
                {
                    Toast.makeText(NewNoteActivity.this, "Note Saved Succesfuly", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(NewNoteActivity.this, "Error!!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        Discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewNoteActivity.this, "Note Discarded", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
