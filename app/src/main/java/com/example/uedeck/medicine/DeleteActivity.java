package com.example.uedeck.medicine;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    private boolean validation()
    {
        if(name.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    EditText name;
    Button delete;
    DBHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        name=(EditText) findViewById(R.id.deleteName);
        delete=(Button) findViewById(R.id.delete);
        helper=new DBHelper(this);
        db=helper.getWritableDatabase();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation())
                {
                db.delete(DBHelper.TABLE_NAME,DBHelper.KEY_NAME+"=?",new String[]{String.valueOf(name.getText().toString())});
                Toast.makeText(getApplicationContext(),"Удаление прошло успешно",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Заполните поле",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
