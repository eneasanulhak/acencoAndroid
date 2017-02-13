package com.example.eneasseven.acenco.Services;

import java.io.Serializable;

/**
 * Created by Eneas.Seven on 28/01/2017.
 */

public class InfoService implements Serializable {
    private boolean ativo = false;
    private String ip;
    private int porta;

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}
