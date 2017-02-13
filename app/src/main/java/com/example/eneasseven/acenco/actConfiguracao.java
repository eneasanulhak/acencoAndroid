package com.example.eneasseven.acenco;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;

import com.example.eneasseven.acenco.dao.DaoConfiguracao;
import com.example.eneasseven.acenco.database.DataBase;
import com.example.eneasseven.acenco.model.Configuracao;

public class actConfiguracao extends AppCompatActivity {

    EditText editTextIP;
    EditText editTextPorta;
    EditText editTextUrl;

    private DataBase db;
    private SQLiteDatabase conn;
    private DaoConfiguracao daoConfiguracao;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_configuracao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextIP = (EditText)findViewById(R.id.editTextIP);
        editTextPorta = (EditText)findViewById(R.id.editTextPorta);
        editTextUrl = (EditText)findViewById(R.id.editTextUrl);

        try {
            db = new DataBase(this);
            conn = db.getWritableDatabase();
            daoConfiguracao = new DaoConfiguracao(conn);
            configuracao = daoConfiguracao.buscaConfiguracao(this);
            editTextIP.setText(configuracao.getIp());
            editTextUrl.setText(configuracao.getUrl());
            editTextPorta.setText(String.valueOf(configuracao.getPorta()));

        } catch (SQLException ex) {

            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setNeutralButton("OK",null);
            msg.setMessage("ERRO: " + ex.toString());
            msg.show();
        }

    }

    @Override
    protected void onDestroy() {
        configuracao.setIp(editTextIP.getText().toString());
        configuracao.setPorta(Integer.valueOf(editTextPorta.getText().toString()));
        configuracao.setUrl(editTextUrl.getText().toString());
        daoConfiguracao.alterar(configuracao);
        super.onDestroy();
    }
}
