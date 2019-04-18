package br.com.cepbrazopolis.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.Helper.DataBaseHelper;
import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.ListaContato;
import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.domain.Contato;


public class EditaExcluiContato extends Activity {
    private Button btn_editar, btn_excluir;
    private EditText nome_edicao, telefone_edicao;
    private Intent intent;
    private Bundle bundle;
    private String id_selecionado;
    private DataBaseHelper dbh;
    private SQLiteDatabase database;
    private Cursor cursor;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_editar_excluir);

        btn_editar = (Button) findViewById(R.id.btn_editar);
        btn_excluir = (Button) findViewById(R.id.btn_excluir);

        nome_edicao = (EditText) findViewById(R.id.nome_edicao);
        telefone_edicao = (EditText) findViewById(R.id.telefone_edicao);

        intent = getIntent();
        bundle = intent.getExtras();

        if(bundle == null){
            CharSequence msg = "Não foi possivel recuperar o id do contato";

            Toast.makeText(
                    this,
                    msg,
                    Toast.LENGTH_SHORT
            ).show();

        }else{
            id_selecionado = bundle.get("id_contato").toString();
        }

        if (Integer.valueOf(id_selecionado) > 0){
            contato = new Contato();
            dbh = new DataBaseHelper(this);
            database = dbh.getWritableDatabase();
            cursor = database.rawQuery(
                    "SELECT * FROM contato WHERE _id = ?",
                    new String[]{id_selecionado});

            if(cursor != null){
                cursor.moveToFirst();
                contato.setId(cursor.getString(0));
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                nome_edicao.setText(contato.getNome());
                telefone_edicao.setText(contato.getTelefone());

            }else{
                CharSequence msg = "Nenhum registro encontrado";
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            }

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbh.close();
    }


    public void SalvarAtualizar(View view){
        String nome_update = nome_edicao.getText().toString();
        String telefone_update = telefone_edicao.getText().toString();

        if(nome_update.equals(" ")||telefone_update.equals(" ")){
            CharSequence charSequence = "Verifique o preenchimento de todos os campos";
            Toast.makeText(this,charSequence,Toast.LENGTH_SHORT).show();
        }else{
            ContentValues valores = new ContentValues();
            this.contato.setNome(nome_update);
            this.contato.setTelefone(telefone_update);
            valores.put("nome",this.contato.getNome());
            valores.put("telefone", this.contato.getTelefone());
            long resultado = database.update(
                    "contato",
                    valores,
                    "_id = ?",
                    new String[]{contato.getId()});

            if(resultado != -1){
                CharSequence msg = "Dados Atualizados com sucesso !!!";
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
                nome_edicao.setText("");
                telefone_edicao.setText("");
                Intent intent = new Intent(this, ListaContato.class);
                intent.putExtra("atualizar_lista",true);
                startActivity(intent);

            }else {
                CharSequence msg = "Falha ao atualizar dados!!!";
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void DeletaRegistro(View view){
        final String opcao = "";
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Exclusao");
        alert.setMessage("Confirma Exclusão de registro?");
        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long res = database.delete(
                        "contato",
                        "_id = ?",
                        new String[]{id_selecionado}
                );

                if(res == -1){
                    CharSequence msg = "Falha ao excluir registro";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }else{
                    CharSequence msg = "Registo excluido com sucesso!!!";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    nome_edicao.setText("");
                    telefone_edicao.setText("");
                    Intent intent = new Intent(getApplicationContext(), ListaContato.class);
                    intent.putExtra("atualizar_lista",true);
                    startActivity(intent);
                }

            }
        });
        alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();





    }
}
