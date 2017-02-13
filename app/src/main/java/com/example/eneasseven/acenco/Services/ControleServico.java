package com.example.eneasseven.acenco.Services;

import android.app.Service;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Eneas.Seven on 28/01/2017.
 */

public class ControleServico extends Binder {

    private RetornoListener servico;

    public ControleServico(RetornoListener servico) {
        this.servico = servico;
    }

    public RetornoListener getServico(){
        return servico;
    }
}
