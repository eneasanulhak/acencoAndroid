package com.example.eneasseven.acenco.model;

/**
 * Created by Eneas.Seven on 22/01/2017.
 */

public class Tarefas {

    int _id;
    int id_servico;
    String servico;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId_servico() {
        return id_servico;
    }

    public void setId_servico(int id_servico) {
        this.id_servico = id_servico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }
}
