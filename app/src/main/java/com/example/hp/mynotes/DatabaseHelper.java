package com.example.hp.mynotes;

/**
 * Created by hp on 11/20/2019.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyNotes.db";
    public static final String TABLE_NAME = "Notes_table";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_NOTE = "NOTE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (" +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +COL_TITLE+ " TEXT, " +COL_NOTE+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public Cursor getTitleData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+COL_ID+", "+COL_TITLE+" from " + TABLE_NAME, null);
        return res;
    }
    public Cursor TitleData(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+COL_TITLE+" from " + TABLE_NAME+" where "+COL_ID+"='"+value+"'", null);
        return res;
    }
    public Cursor NoteData(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+COL_NOTE+" from " + TABLE_NAME+" where "+COL_ID+"='"+value+"'", null);
        return res;
    }

    public void RemoveData(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where "+COL_ID+" = '"+value+"'");
    }

    public boolean updateData(String value,String Title,String Note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, Title);
        contentValues.put(COL_NOTE, Note);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { value });
        return true;
    }

    public boolean insertData(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
