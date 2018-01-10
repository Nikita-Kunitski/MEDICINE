package com.example.uedeck.medicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AllItemsActivity extends AppCompatActivity {

    DBHelper helper;
    ListView list;
    SQLiteDatabase db;
    Cursor medicine;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        helper=new DBHelper(this);
        list=(ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String arr;
                Cursor c=db.query(DBHelper.TABLE_NAME,null,null,null,null,null,null,null);
                    int indexName=c.getColumnIndex(DBHelper.KEY_NAME);
                    for(int i=0;i<=position;i++)
                        c.moveToNext();
                    arr=c.getString(indexName);
                Intent intent=new Intent(AllItemsActivity.this,InfoActivity.class);
                intent.putExtra("name",arr);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        medicine =  db.query(DBHelper.TABLE_NAME,null,null,null,null,null,null);
        String[] headers = new String[] {DBHelper.KEY_NAME, DBHelper.KEY_TEXT};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                medicine, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        list.setAdapter(userAdapter);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void onDestroy(){
        super.onDestroy();
        db.close();
        medicine.close();
    }
}
