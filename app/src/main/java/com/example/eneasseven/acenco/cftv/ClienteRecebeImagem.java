package com.example.eneasseven.acenco.cftv;

import android.util.Log;

import java.io.*;
import java.net.*;

public class ClienteRecebeImagem {

	private Socket client; 	
    private String ip = "";
	
    public ClienteRecebeImagem(String ip, int camera) {
    	this.ip = ip;
		String ipServidor = this.ip;
		int    portaServidor = 9000 + camera;
		
		try {
			client = new Socket(ipServidor, portaServidor);
		} catch (UnknownHostException e) {
			Log.e("Servico",e.toString());
		} catch (IOException e) {
			Log.e("Servico",e.toString());
		}
	}
    
	public String IpServidor(String s){
    	return s.replace("localhost", this.ip);
    }
	
	public void FecharConexao() {
		
		try {
			if (client.isConnected()) {
				client.close();
			}
		} catch (IOException ex) {
			Log.e("Servico",ex.toString());
		}
	}
	
	public byte[] getImagem()
	{
	   InputStream in;
	   byte[] data = null;
		 
	   try {
		    if (client.isConnected()) {
				in = client.getInputStream();
				DataInputStream dis = new DataInputStream(in);
				 
				 int len = dis.readInt();
				 data = new byte[len];
				 if (len > 0) {
				    dis.readFully(data);
				 }
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;		
	}
}
