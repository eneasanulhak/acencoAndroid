package com.example.eneasseven.acenco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eneasseven.acenco.model.Tarefas;

/**
 * Created by Eneas.Seven on 22/01/2017.
 */

public class DaoTarefas {

    private SQLiteDatabase conn;

    public DaoTarefas(SQLiteDatabase conn) {
         this.conn = conn;
     }

    public Tarefas buscaTarefa(Context context) {
        Tarefas tarefa = new Tarefas();
        Cursor cur = conn.query("TAREFAS",null,null,null,null,null,null);
        if (cur.getCount() > 0){
            cur.moveToFirst();
            tarefa.set_id(cur.getInt(cur.getColumnIndex("_id")));
            tarefa.setId_servico(cur.getInt(cur.getColumnIndex("ID_SERVICO")));
            tarefa.setServico(cur.getString(cur.getColumnIndex("SERVICO")));
        }
        return tarefa;
    }

    public void inserir(Tarefas tarefa) {
        ContentValues values = new ContentValues();
        values.put("ID_SERVICO",tarefa.getId_servico());
        values.put("SERVICO", tarefa.getServico());
        long _id = conn.insert("TAREFAS",null, values);
    }

    public void alterar(Tarefas tarefa) {
        ContentValues values = new ContentValues();
        values.put("ID_SERVICO",tarefa.getId_servico());
        values.put("SERVICO", tarefa.getServico());
        conn.update("TAREFAS", values, "_id = ?",new String[]{String.valueOf(tarefa.get_id())});
    }

    public void apagar(Tarefas tarefa){
        conn.delete("TAREFAS","_id = ?",new String[]{String.valueOf(tarefa.get_id())});
    }
}
