package com.example.eneasseven.acenco.database;

/**
 * Created by Eneas.Seven on 21/01/2017.
 */

public class ScriptSQL {

    public static String getCreateConfiguracao() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS CONFIGURACAO ( " );
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("IP VARCHAR(100), ");
        sql.append("PORTA INT, ");
        sql.append("URL VARCHAR(100));");
        return sql.toString();
    }

    public static String getCreateTarefas() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS TAREFAS ( " );
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("ID_SERVICO INT, ");
        sql.append("SERVICO VARCHAR(50));");
        return sql.toString();
    }
}
