package com.example.eneasseven.acenco;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.example.eneasseven.acenco.Services.ControleServico;
import com.example.eneasseven.acenco.Services.InfoService;
import com.example.eneasseven.acenco.Services.ServiceCliente;
import com.example.eneasseven.acenco.dao.*;
import com.example.eneasseven.acenco.database.DataBase;
import com.example.eneasseven.acenco.dto.DtoDadosRecebidos;
import com.example.eneasseven.acenco.model.*;

import java.io.Serializable;

public class actPrincipal extends AppCompatActivity implements ServiceConnection{

     //inclusao no git

    private Button buttonServico;
    private Button buttonCamera1;
    private Button buttonCamera2;
    private Button buttonCamera3;
    private Button buttonCamera4;

    private DataBase db;
    private SQLiteDatabase conn;
    private TextView textViewServico;
    private DaoTarefas daoTarefas;
    private Tarefas tarefa;
    public ServiceConnection serviceConnection;
    private InfoService info;
    private Configuracao configuracao;
    private DaoConfiguracao daoConfiguracao;

    public static final String LABEL_BOTAO_SERVICE_INICIAR = "Iniciar";
    public static final String LABEL_BOTAO_SERVICE_PARAR = "Parar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonServico = (Button) findViewById(R.id.buttonServico);

        buttonCamera1 = (Button) findViewById(R.id.buttonCamera1);
        buttonCamera2 = (Button) findViewById(R.id.buttonCamera2);
        buttonCamera3 = (Button) findViewById(R.id.buttonCamera3);
        buttonCamera4 = (Button) findViewById(R.id.buttonCamera4);

        textViewServico = (TextView)findViewById(R.id.textViewServico);

        try {
            DataBase db = new DataBase(this);
            conn = db.getReadableDatabase();

        } catch (SQLException ex) {
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setNeutralButton("OK",null);
            msg.setMessage("ERRO: " + ex.toString());
            msg.show();
        }

        controlaExecucaoServico();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(R.mipmap.ic_launcher);

        daoConfiguracao = new DaoConfiguracao(conn);
        configuracao = daoConfiguracao.buscaConfiguracao(this);
        info.setIp(configuracao.getIp());
        info.setPorta(configuracao.getPorta());

        Intent intent = getIntent();
        DtoDadosRecebidos dtoDadosRecebidos = (DtoDadosRecebidos) intent.getSerializableExtra("dados");
        if (dtoDadosRecebidos != null){
            Intent camera = new Intent("com.example.eneasseven.acenco.camera");
            camera.putExtra("dados",dtoDadosRecebidos);
            camera.putExtra("ip",info.getIp());
            startActivity(camera);
        }


    }

     public void onClickButtonServico(View v) {

        if (v == buttonServico ) {
            //Intent servico = new Intent("com.example.eneasseven.acenco.serviceCliente");
            Intent servico = new Intent(this, ServiceCliente.class);
            if (!info.isAtivo()) {
                info.setAtivo(false);
                servico.putExtra("info", info);
                startService(servico);
            } else {
                stopService(servico);
                unbindService(serviceConnection);
                onServiceDisconnected(null);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_configuracao) {

            Intent it = new Intent(this, actConfiguracao.class);
            startActivity(it);

            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void controlaExecucaoServico() {
        serviceConnection = this;
        info = new InfoService();
        //bindService(new Intent("com.example.eneasseven.acenco.serviceCliente"), serviceConnection,0);
        bindService(new Intent(this, ServiceCliente.class), serviceConnection,0);

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i("Servico","onServiceConnected");
        ControleServico retorno = (ControleServico) service;
        info = (InfoService) retorno.getServico().retornoServico();
        controleBotao(info.isAtivo());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("Servico","onServiceDisconnected");
        info.setAtivo(false);
        controleBotao(info.isAtivo());
    }

    private void controleBotao(boolean ativo){
        if (ativo) {
            buttonServico.setText(actPrincipal.LABEL_BOTAO_SERVICE_PARAR);
            textViewServico.setText("Conectado!!");
        } else {
            buttonServico.setText(actPrincipal.LABEL_BOTAO_SERVICE_INICIAR);
            textViewServico.setText("Desconectado!!");

        }
    }

    public void executaCamera1(View view){
        Intent intent = new Intent("com.example.eneasseven.acenco.camera");
        intent.putExtra("ip",info.getIp());
        if (view == buttonCamera1) {
            intent.putExtra("dados",new DtoDadosRecebidos("Identificar-Descricao-Classe-0,0,camera1,0,1,0,0-1-1"));
        } else if (view == buttonCamera2) {
            intent.putExtra("dados",new DtoDadosRecebidos("Identificar-Descricao-Classe-0,0,camera2,0,2,0,0-1-1"));
        } else if (view == buttonCamera3) {
            intent.putExtra("dados", new DtoDadosRecebidos("Identificar-Descricao-Classe-0,0,camera3,0,3,0,0-1-1"));
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }
}
