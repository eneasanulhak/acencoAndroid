package com.example.eneasseven.acenco.model;

/**
 * Created by Eneas.Seven on 22/01/2017.
 */

public class Configuracao {


    int _id;
    int porta;
    String ip;
    String url;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
