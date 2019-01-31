package br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.com.cepbrazopolis.agenda.EditaExcluiContato;
import br.com.cepbrazopolis.agenda.R;
import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.Helper.DataBaseHelper;


public class ListaContato extends Activity {
    private ListView listView;
    private DataBaseHelper dbh;
    private SQLiteDatabase database;
    private Cursor cursor;
    private SimpleCursorAdapter simpleCursorAdapter;
    private Boolean atualizar_lista = false;
    private String id;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_lista);
        listView = (ListView) findViewById(R.id.lista_contatos);

        dbh = new DataBaseHelper(this);

        database = dbh.getReadableDatabase();

        String[] campos = new String[]{
                "_id",
                "nome",
                "telefone"

        };

        cursor = database.query(
                "contato",
                campos,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0){
            cursor.moveToFirst();


            simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.agenda_lista_aux,
                    cursor,
                    campos,
                    new int[]{R.id.id_contato,R.id.nome_contato,R.id.telefone_contato}
                    );
            listView.setAdapter(simpleCursorAdapter);
        }else{
            Toast.makeText(
                    this,
                    "Nenhum registro encontrado",
                    Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) listView.getAdapter().getItem(i);
                id = c.getString(0);
                Intent intent = new Intent(getBaseContext(), EditaExcluiContato.class);
                intent.putExtra("id_contato", id);
                startActivity(intent);
            }
        });

        atualizar_lista = getIntent().getBooleanExtra("atualizar_lista",false);
            if(atualizar_lista == true) {
                AtualizaListView();
            }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void AtualizaListView() {


        dbh = new DataBaseHelper(this);

        database = dbh.getReadableDatabase();

        String[] campos = new String[]{
                "_id",
                "nome",
                "telefone"

        };

        cursor = database.query(
                "contato",
                campos,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            cursor.moveToLast();


            simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.agenda_lista_aux,
                    cursor,
                    campos,
                    new int[]{R.id.id_contato, R.id.nome_contato, R.id.telefone_contato}
            );
            simpleCursorAdapter.notifyDataSetChanged();
            listView.invalidateViews();
            listView.setAdapter(simpleCursorAdapter);

        }
    }
}
