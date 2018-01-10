package com.example.uedeck.medicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView name;
    TextView category;
    TextView substance;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        name=(TextView) findViewById(R.id.textName);
        category=(TextView)findViewById(R.id.textCategory);
        substance=(TextView) findViewById(R.id.textSubstance);
        description=(TextView) findViewById(R.id.textDescription);
        String name_S=getIntent().getExtras().getString("name");
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TABLE_NAME,null,DBHelper.KEY_NAME+"=?",new String[]{name_S},null,null,null);
        if(cursor.moveToFirst())
        {
            int idName=cursor.getColumnIndex(DBHelper.KEY_NAME);
            int idCategory=cursor.getColumnIndex(DBHelper.KEY_CATEGORY);
            int idSubstation=cursor.getColumnIndex(DBHelper.KEY_SUBSTANCE);
            int idDescription=cursor.getColumnIndex(DBHelper.KEY_TEXT);
            name.setText(cursor.getString(idName));
            category.setText(cursor.getString(idCategory));
            substance.setText(cursor.getString(idSubstation));
            description.setText(cursor.getString(idDescription));
        }
    }
}
