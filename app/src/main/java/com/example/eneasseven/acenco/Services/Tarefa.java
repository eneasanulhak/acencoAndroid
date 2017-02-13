package com.example.eneasseven.acenco.Services;
import android.app.Service;


public class Tarefa implements Runnable{

    private int id;

    public Tarefa() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public Tarefa(int id) {
        this.id = id;
    }

    @Override
    public void run() {

    }
}
