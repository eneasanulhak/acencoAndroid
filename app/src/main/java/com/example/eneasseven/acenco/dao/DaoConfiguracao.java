package com.example.eneasseven.acenco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.database.sqlite.*;

import com.example.eneasseven.acenco.model.Configuracao;

/**
 * Created by Eneas.Seven on 22/01/2017.
 */

public class DaoConfiguracao {

    private SQLiteDatabase conn;

    public DaoConfiguracao(SQLiteDatabase conn) {
         this.conn = conn;
     }

    public Configuracao buscaConfiguracao(Context context) {
        Configuracao configuracao = new Configuracao();
        Cursor cur = conn.query("CONFIGURACAO",null,null,null,null,null,null);
        if (cur.getCount() > 0){
            cur.moveToFirst();
            configuracao.set_id(cur.getInt(cur.getColumnIndex("_id")));
            configuracao.setIp(cur.getString(cur.getColumnIndex("IP")));
            configuracao.setPorta(cur.getInt(cur.getColumnIndex("PORTA")));
            configuracao.setUrl(cur.getString(cur.getColumnIndex("URL")));
        }
        return configuracao;
    }

    public void inserir(Configuracao configuracao) {
        ContentValues values = new ContentValues();
        values.put("IP",configuracao.getIp());
        values.put("PORTA", configuracao.getPorta());
        values.put("URL",configuracao.getUrl());
        long _id = conn.insert("CONFIGURACAO",null, values);
    }

    public void alterar(Configuracao configuracao) {
        ContentValues values = new ContentValues();
        values.put("IP",configuracao.getIp());
        values.put("PORTA", configuracao.getPorta());
        values.put("URL",configuracao.getUrl());
        conn.update("CONFIGURACAO", values, "_id = ?",new String[]{String.valueOf(configuracao.get_id())});
    }
}
