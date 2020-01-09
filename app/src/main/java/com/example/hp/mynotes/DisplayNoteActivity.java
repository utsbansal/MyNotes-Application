package com.example.hp.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
public class DisplayNoteActivity extends AppCompatActivity{

    String value="";
    EditText Title, Note;
    Button Delete, Send, Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        Title = (EditText) findViewById(R.id.title_id);
        Note = (EditText) findViewById(R.id.note_id);
        Delete = (Button)findViewById(R.id.delete_bt);
        Send = (Button)findViewById(R.id.send_bt);
        Update = (Button)findViewById(R.id.update_bt);

        Bundle info=getIntent().getExtras();
        if(info!=null){
            value=info.getString("ID");
        }
        final DatabaseHelper myDb=new DatabaseHelper(DisplayNoteActivity.this);
        Cursor res = myDb.TitleData(value);
        if(res.getCount() == 0) {
            // show message
            //information.setText("Error Nothing found");
            Toast.makeText(getApplicationContext(),"Error Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        while(res.moveToNext()){
            Title.setText(res.getString(res.getColumnIndex(DatabaseHelper.COL_TITLE)));
        }
        res = myDb.NoteData(value);
        if(res.getCount()==0){
            Toast.makeText(getApplicationContext(),"Error Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        while(res.moveToNext()){
            Note.setText(res.getString(res.getColumnIndex(DatabaseHelper.COL_NOTE)));
        }

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.RemoveData(value);
                Toast.makeText(getApplicationContext(),"Note Deleted Succesfully",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = Title.getText().toString();
                String body = Note.getText().toString();
                Intent intent = new Intent(DisplayNoteActivity.this, SendEmailActivity.class);
                intent.putExtra("Subject", head);
                intent.putExtra("Message", body);
                startActivity(intent);

            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = Title.getText().toString();
                String body = Note.getText().toString();
                DatabaseHelper  mysqliteopenhelper = new DatabaseHelper(getApplicationContext());
                Boolean flag = mysqliteopenhelper.updateData(value, head, body);
                if(flag){
                    Toast.makeText(getApplicationContext(),"Note Saved Succesfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Note Could not be saved",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
