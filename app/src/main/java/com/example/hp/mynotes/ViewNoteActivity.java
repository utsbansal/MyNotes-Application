package com.example.hp.mynotes;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewNoteActivity extends AppCompatActivity {

    String value="";
    TextView information;
    ListView Titles;
    String[] listItem;
    ArrayList<String> NoteID= new ArrayList<String>();
    ArrayList<String> Title= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Titles=(ListView)findViewById(R.id.list_id);

        DatabaseHelper myDb=new DatabaseHelper(ViewNoteActivity.this);
        Cursor res = myDb.getTitleData();
        if(res.getCount() == 0) {
            // show message
            //information.setText("Error Nothing found");
            Toast.makeText(getApplicationContext(),"Error Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        while(res.moveToNext()){
            NoteID.add(res.getString(res.getColumnIndex(DatabaseHelper.COL_ID)));
            Title.add(res.getString(res.getColumnIndex(DatabaseHelper.COL_TITLE)));
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Title);
        Titles.setAdapter(adapter);

        Titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value = NoteID.get(position);
                Intent intent = new Intent(ViewNoteActivity.this, DisplayNoteActivity.class);
                //Toast.makeText(getApplicationContext(),"Position "+value,Toast.LENGTH_LONG).show();
                intent.putExtra("ID", value);
                startActivity(intent);


            }
        });
    }
}
