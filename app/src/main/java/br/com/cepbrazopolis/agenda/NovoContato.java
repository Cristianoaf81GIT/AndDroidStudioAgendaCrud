package br.com.cepbrazopolis.agenda;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.Helper.DataBaseHelper;
import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.domain.Contato;


public class NovoContato extends Activity {

    private EditText txt_nome, txt_telefone;
    private Button btn_novo_contato;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_cadastro);
        txt_nome = (EditText) findViewById(R.id.nome);
        txt_telefone = (EditText) findViewById(R.id.telefone);
        btn_novo_contato = (Button) findViewById(R.id.btn_salvar);
    }

    public void SalvarContato(View view){
        //cria obj contato e popula seus campos
        Contato contato = new Contato();
        contato.setNome(txt_nome.getText().toString());
        contato.setTelefone(txt_telefone.getText().toString());
        txt_nome.setText("");
        txt_telefone.setText("");


        //cria a base de dados
        DataBaseHelper dbh = new DataBaseHelper(this);

        //recupera banco de dados para manipulação
        SQLiteDatabase db = dbh.getWritableDatabase();

        //prepara os dados para inserir no bd
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());

        //realiza o insert e verifica se tudo esta ok
        long resultado = db.insert("contato",null, values);

        if(resultado == -1){
            Toast toast = new Toast(this);
            toast.setText("Erro ao salvar Registro!");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Toast.makeText(
                    this,
                    "Registro Salvo com sucesso!!!",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
}
