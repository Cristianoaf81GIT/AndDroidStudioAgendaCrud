package br.com.cepbrazopolis.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.ListaContato;


public class Principal extends Activity {

    private Button btn_contato, btn_listar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_principal);
        btn_contato = (Button) findViewById(R.id.btn_contato);
        btn_listar = (Button) findViewById(R.id.btn_listar);
    }

    public void novo_contato(View view){
     startActivity(new Intent(this, NovoContato.class));
    }

    public  void listar_contatos(View view){
        startActivity(new Intent(this, ListaContato.class));
    }
}
