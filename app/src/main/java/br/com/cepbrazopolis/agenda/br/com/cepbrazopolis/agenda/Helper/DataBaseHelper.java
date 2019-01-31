package br.com.cepbrazopolis.agenda.br.com.cepbrazopolis.agenda.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "Create Table if not Exists contato(" +
                "_id integer not null primary key autoincrement," +
                "nome text," +
                "telefone text" +
                ")";

        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public DataBaseHelper(Context context){
        super(context, "agenda.db",null,1);
    }
}
