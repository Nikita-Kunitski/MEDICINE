package com.example.uedeck.medicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    TextView cat;
    ListView lv;
    SQLiteDatabase db;
    DBHelper helper;
    Cursor category;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        cat=(TextView) findViewById(R.id.cat);
        lv=(ListView) findViewById(R.id.listCategory);
        helper=new DBHelper(this);
        db=helper.getReadableDatabase();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String arr;
                Cursor c=db.rawQuery("Select * from medicinal",null);
                int indexName=c.getColumnIndex(DBHelper.KEY_NAME);
                for(int i=0;i<=position;i++)
                    c.moveToNext();
                arr=c.getString(indexName);
                Intent intent=new Intent(CategoryActivity.this,InfoActivity.class);
                intent.putExtra("name",arr);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        category =  db.rawQuery("Select * from medicinal",null);
        String[] headers = new String[] {DBHelper.KEY_NAME, DBHelper.KEY_TEXT};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                category, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        lv.setAdapter(userAdapter);
    }
    public void onDestroy(){
        super.onDestroy();
        db.close();
        category.close();
    }
}
