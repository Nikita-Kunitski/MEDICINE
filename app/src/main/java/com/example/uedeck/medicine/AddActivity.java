package com.example.uedeck.medicine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    DBHelper helper;
    EditText name;
    EditText substation;
    EditText text;
    Button add;
    SQLiteDatabase database;
    ContentValues cv;
    Spinner spinner;
    Cursor medicine;
    SimpleCursorAdapter userAdapter;

    private boolean validation()
    {
        if(name.getText().toString().isEmpty()||substation.getText().toString().isEmpty()||text.getText().toString().isEmpty()) {
            return false;
        }else
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=(EditText) findViewById(R.id.name);
        substation=(EditText) findViewById(R.id.substation);
        text=(EditText) findViewById(R.id.text);
        add=(Button) findViewById(R.id.add);
        spinner=(Spinner) findViewById(R.id.spinner);
        helper=new DBHelper(this);
        database=helper.getWritableDatabase();
        cv=new ContentValues();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation())
                {
                    String selectSpinner=spinner.getSelectedItem().toString();
                    database.execSQL("insert into "+DBHelper.TABLE_NAME+" (" +
                            DBHelper.KEY_NAME+"," +
                            DBHelper.KEY_CATEGORY+"," +
                            DBHelper.KEY_SUBSTANCE+"," +
                            DBHelper.KEY_TEXT+")" +
                            "values('"+name.getText().toString()+"','" +
                            selectSpinner+"','" +
                            substation.getText().toString()+"','" +
                            text.getText().toString()+"');");
                    Toast.makeText(getApplicationContext(),"Данные успешно записаны в БД",Toast.LENGTH_SHORT).show();
                    name.setText("");
                    substation.setText("");
                    text.setText("");
                }
                else Toast.makeText(getApplicationContext(),"Заполните все поля",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        database = helper.getReadableDatabase();
        medicine =  database.query(DBHelper.TABLE_CATEGORY,new String[]{DBHelper.KEY_CATEGORY},null,null,null,null,null);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        if(medicine.moveToFirst())
        {
            int category=medicine.getColumnIndex(DBHelper.KEY_CATEGORY);
            do {
                adapter.add(medicine.getString(category));
            }while (medicine.moveToNext());
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(AddActivity.this, R.color.colorAccent));
                ((TextView) parent.getChildAt(0)).setTextSize(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void onDestroy(){
        super.onDestroy();
        database.close();
        medicine.close();
    }
}
