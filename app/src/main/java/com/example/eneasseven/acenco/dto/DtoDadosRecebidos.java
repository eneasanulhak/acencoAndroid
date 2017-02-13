package com.example.eneasseven.acenco.dto;

import java.io.Serializable;

/**
 * Created by Eneas.Seven on 31/01/2017.
 */

public class DtoDadosRecebidos  implements Serializable{

    private String descricao;
    private String classe;
    private String parametros;
    private int habilitado;
    private int tela;

    public DtoDadosRecebidos(String mensagem) {

        //Receber -> Identificar-Descricao-Classe-Parametros-Habilitado-Tela;...
        String[] campos   = mensagem.split("-");

        descricao 	= campos[1]; //rs.getString("Descricao");
        classe 		= campos[2]; //rs.getString("Classe");
        parametros 	= campos[3]; //rs.getString("Parametros");
        habilitado 	= Integer.parseInt(campos[4]); //rs.getInt("Habilitado");
        tela  		= Integer.parseInt(campos[5]); //rs.getInt("Tela");
    }

    public String getDescricao() {
        return descricao;
    }

    public String getClasse() {
        return classe;
    }

    public String getParametros() {
        return parametros;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public int getTela() {
        return tela;
    }
}
