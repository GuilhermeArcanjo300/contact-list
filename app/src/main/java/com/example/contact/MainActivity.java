package com.example.contact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton btnAdd;
    private SQLiteDatabase bancoDados;
    public ArrayList<Integer> arrayIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);

        criarBancoDados();
        // inserirDadosTemp();
        listarDados();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Excluir");
                alert.setMessage("Tem certeza que deseja excluir esse contato?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(i);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editar(i);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
            //bancoDados.execSQL("DROP TABLE contact");
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS contact(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , nome VARCHAR" +
                    " , email VARCHAR" +
                    " , phone VARCHAR" +
                    " , active INTEGER)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try {

            bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, email, phone, active FROM contact", null);
            ArrayList<String> row = new ArrayList<String>();

            ArrayList<User> userArray = new ArrayList<User>();
            CustomListAdapter customListAdapter = new CustomListAdapter(this, userArray);

            arrayIds = new ArrayList<>();
            meuCursor.moveToFirst();
            do {
                User user = new User();
                user.setName(meuCursor.getString(1));
                user.setEmail(meuCursor.getString(2));
                user.setPhone(meuCursor.getString(3));
                user.setActive(meuCursor.getInt(4));
                userArray.add(user);

                arrayIds.add(meuCursor.getInt(0));
            } while(meuCursor.moveToNext());

            listView.setAdapter(customListAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    public void excluir(Integer i){
        try{
            bancoDados = openOrCreateDatabase("contact", MODE_PRIVATE, null);
            String sql = "DELETE FROM contact WHERE id =?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, arrayIds.get(i));
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editar(Integer i){
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("id",arrayIds.get(i));
        startActivity(intent);
    }
}