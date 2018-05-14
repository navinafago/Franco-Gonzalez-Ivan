package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 05/01/2016.
 */
public class sesionModel {

    private int idSesion;

    private int idUsuario;

    private int activa;

    public int getIdSesion(){
        return idSesion;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public int getActiva(){
        return activa;
    }

    public void setIdSesion( int idSn){
        idSesion = idSn;
    }

    public void setIdUsuario( int idUr){
        idUsuario = idUr;
    }

    public void setActiva( int av){
        activa = av;
    }
}
