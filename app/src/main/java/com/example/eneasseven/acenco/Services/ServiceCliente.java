package com.example.eneasseven.acenco.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.eneasseven.acenco.dto.DtoDadosRecebidos;

import java.util.ArrayList;
import java.util.List;

public class ServiceCliente extends Service implements RetornoListener {

    private List<Runnable> tarefas = new ArrayList<Runnable>();
    private int startId;
    private ControleServico controleServico;
    private InfoService info;

    public ServiceCliente() {
        controleServico = new ControleServico(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return controleServico;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Servico","Start comando Tarefa!!");
        this.startId = startId;
        this.info = (InfoService) intent.getSerializableExtra("info");
        this.info.setAtivo(true);
        Runnable tarefa = new Cliente(startId, info.getIp(), info.getPorta(), this);
        tarefas.add(tarefa);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("Servico","Destroy Servico!!");
        stopSelf(startId);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i("Servico","Create Servico!");
        super.onCreate();
    }

    @Override
    public InfoService retornoServico() {
         return info;
    }

    public void sendNotificacao(DtoDadosRecebidos dtoDadosRecebidos){
        Intent intent = new Intent("com.example.eneasseven.acenco.broadCastNotificacao");
        intent.putExtra("dados", dtoDadosRecebidos);
        sendBroadcast(intent);
    }
}
