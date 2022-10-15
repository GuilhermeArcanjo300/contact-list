package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText editTextNome, editTextEmail, editTextPhone;
    Button btnInsert;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        btnInsert = (Button) findViewById(R.id.btnEdit);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarAnimal();
            }
        });

    }

    public void cadastrarAnimal(){
            if(!TextUtils.isEmpty(editTextNome.getText().toString())){
                try{
                    bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
                    String sql = "INSERT INTO contact (nome, email, phone, active) VALUES (?, ?, ?, ?)";
                    SQLiteStatement stmt = bancoDados.compileStatement(sql);
                    stmt.bindString(1,editTextNome.getText().toString());
                    stmt.bindString(2,editTextEmail.getText().toString());
                    stmt.bindString(3,editTextPhone.getText().toString());
                    stmt.bindLong(4,1);
                    stmt.executeInsert();
                    bancoDados.close();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
}