package com.example.eneasseven.acenco.database;

/**
 * Created by Eneas.Seven on 21/01/2017.
 */

import android.content.ContentValues;
import android.database.sqlite.*;
import android.content.Context;

import com.example.eneasseven.acenco.dao.DaoConfiguracao;
import com.example.eneasseven.acenco.model.Configuracao;

public class DataBase extends SQLiteOpenHelper {


    public DataBase (Context context){
        super(context, "Acenco", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateConfiguracao());
        db.execSQL(ScriptSQL.getCreateTarefas());

        Configuracao configuracao = new Configuracao();
        configuracao.setUrl("RTSP://");
        configuracao.setPorta(8888);
        configuracao.setIp("acenco1.ddns.net");
        DaoConfiguracao daoConfiguracao = new DaoConfiguracao(db);
        daoConfiguracao.inserir(configuracao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
