package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 05/01/2016.
 */
public class embarquesModel {

    private int idEmbarque;

    private int idOperacion;

    private int cantidadEmbarcada;

    private String fecEmbarque;

    public int getIdEmbarque(){
        return idEmbarque;
    }

    public int getIdOperacion(){
        return idOperacion;
    }

    public int getCantidadEmbarcada(){
        return cantidadEmbarcada;
    }

    public String getFecEmbarque(){
        return fecEmbarque;
    }

    public void setIdEmbarque( int idEq){
        idEmbarque = idEq;
    }

    public void setIdOperacion( int idOn){
        idOperacion = idOn;
    }

    public void setCantidadEmbarcada(int caEm){
        cantidadEmbarcada = caEm;
    }

    public void setFecEmbarque( String fcEq){
        fecEmbarque = fcEq;
    }
}
