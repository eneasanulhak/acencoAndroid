package com.example.eneasseven.acenco.Services;

import android.content.Intent;
import android.os.Bundle;
import java.lang.reflect.Method;
import android.app.Service;

import com.example.eneasseven.acenco.dto.DtoDadosRecebidos;

public class PMensagem implements Runnable {

	private Cliente cliente = null;
	private boolean processa = true;
	
	public PMensagem(Cliente cli ) {
		this.cliente 	= cli;
		
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {

		while(processa){
			ProcessaMsg();
		}
	}

	private void ProcessaMsg(){

		String Mensagem = "";
		
		if (cliente.getvMsg().size() > 0) {
			
			Mensagem = cliente.getvMsg().get(0);
			cliente.getvMsg().removeElementAt(0);
			
		} 
		
		if (!Mensagem.equals("")) {
			String[] registro = Mensagem.split(";");
			for (int r = 0; r < registro.length; r++){
				DtoDadosRecebidos dtoDadosRecebidos = new DtoDadosRecebidos(registro[r]);
				if (dtoDadosRecebidos.getTela() == 1) {
					cliente.serviceCliente().sendNotificacao(dtoDadosRecebidos);
				}
			}
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException i){}

	}

	public void fechaProcessamentoMensagem(){
		processa = false;
	}

}
