package com.example.eneasseven.acenco.Services;

import android.app.Service;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Cliente implements Runnable {

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String Mensagem = "";
    private Socket client; 	
    private  Vector<String> vMsg = null;
    public String ip = "";
	public synchronized Vector<String> getvMsg() {
		return vMsg;
	}
	private boolean processar = true;
    private PMensagem pm;
	private int porta;
	private int startId;
	private ServiceCliente service;

    public Cliente(int startId, String ip, int porta, ServiceCliente service) {

		this.ip = ip;
		this.porta = porta;
		this.startId = startId;
		this.service = service;

		vMsg = new Vector<String>();

		pm = new PMensagem(this);

		Thread inicio = new Thread(this);
		inicio.start();
		
	}

    public String IpServidor(String s){
    	
    	return s.replace("localhost", this.ip);
    }
	
	public void run() {
		
		String ipServidor = this.ip;
		int    portaServidor = this.porta;

		try {
		
			client = new Socket(ipServidor, portaServidor);
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(client.getInputStream());
			
			Log.i("Servico", "Cliente Conectado!!");

			while (processar) {
				Mensagem = (String) input.readObject();
				vMsg.add(Mensagem);
			}

		} catch (IOException ex) {
			Log.e("Servico",ex.getMessage());

		}
	    catch (ClassNotFoundException ex) {
			Log.e("Servico",ex.getMessage());
	    }
	}

	public void Enviar(String mess){
		try {
			if (client != null) {
				if (client.isConnected()) {
					output.writeObject(mess);
					output.flush();
				}
			}
			
		}catch (IOException ex) { 
			System.out.println(ex.getMessage());
			
		}
	}

	public void FecharConexao() {
		
		try {

			pm.fechaProcessamentoMensagem();
			processar = false;
			output.close();
			input.close();
			client.close();

		} catch (IOException ex) {
			Log.e("Servico", ex.getMessage());
		}
	}

    public ServiceCliente serviceCliente() {
		return this.service;
	}
}
