package com.example.uedeck.medicine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Uedeck on 05.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="medicineDB";
    public static final String TABLE_NAME="medicine";
    public static final String TABLE_CATEGORY="category";

    public static String KEY_ID = "_id";
    public static String KEY_NAME = "name";
    public static String KEY_CATEGORY="_category";
    public static String KEY_TEXT = "description";
    public static String KEY_SUBSTANCE="substance";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_NAME + " ("+
                KEY_ID +" integer primary key , "+
                KEY_NAME + " text, "+
                KEY_CATEGORY +" text, "+
                KEY_SUBSTANCE+" text, "+
                KEY_TEXT+" text," +
                "foreign key("+KEY_CATEGORY+") references "+ TABLE_CATEGORY+" ("+KEY_CATEGORY+"));");

        db.execSQL("create table "+ TABLE_CATEGORY + " ("+
                KEY_CATEGORY + " text," +
                "CONSTRAINT new_pk PRIMARY KEY ("+KEY_CATEGORY+") );");

        db.execSQL("insert into "+TABLE_CATEGORY+"("+KEY_CATEGORY+")" +
                "values('БАД');");
        db.execSQL("insert into "+TABLE_CATEGORY+"("+KEY_CATEGORY+")" +
                "values('Витамины');");
        db.execSQL("insert into "+TABLE_CATEGORY+"("+KEY_CATEGORY+")" +
                "values('Гормональные');");
        db.execSQL("insert into "+TABLE_CATEGORY+"("+KEY_CATEGORY+")" +
                "values('Лекарственные');");
        db.execSQL("CREATE VIEW medicinal AS SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+" like 'Лекарственные';");
        db.execSQL("CREATE VIEW hormone AS SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+" like 'Гормональные';");
        db.execSQL("CREATE VIEW vitamins AS SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+" like 'Витамины';");
        db.execSQL("CREATE VIEW BAA AS SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+" like 'БАД';");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);

        onCreate(db);
    }
}
