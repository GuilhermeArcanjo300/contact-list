package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class EditActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    EditText editTextNome, editTextEmail, editTextPhone;
    Switch switchActivity;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        switchActivity = (Switch) findViewById(R.id.switchActivity);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        loadForm();
    }

    public void loadForm(){
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);
        try {
            bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome, email, phone, active FROM contact WHERE id = " + id.toString(), null);
            cursor.moveToFirst();
            editTextNome.setText(cursor.getString(1));
            editTextEmail.setText(cursor.getString(2));
            editTextPhone.setText(cursor.getString(3));

            if(cursor.getInt(4) == 1){
                switchActivity.setChecked(true);
            }else{
                switchActivity.setChecked(false);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);

        String valueNome, valueEmail, valuePhone;
        int valueActive;
        valueNome = editTextNome.getText().toString();
        valueEmail = editTextEmail.getText().toString();
        valuePhone = editTextPhone.getText().toString();

        if(switchActivity.isChecked()){
            valueActive = 1;
        }else {
            valueActive = 0;
        }

        try{
            bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
            String sql = "UPDATE contact SET nome=?, email=?, phone=?, active=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,valueNome);
            stmt.bindString(2,valueEmail);
            stmt.bindString(3,valuePhone);
            stmt.bindLong(4,valueActive);
            stmt.bindLong(5,id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}