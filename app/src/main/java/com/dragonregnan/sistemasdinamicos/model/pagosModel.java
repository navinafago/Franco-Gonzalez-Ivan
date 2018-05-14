package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 05/01/2016.
 */
public class pagosModel {

    private int idPago;

    private int idOperacion;

    private float cantidadPagada;

    private String fecPago;

    public int getIdPago(){
        return idPago;
    }

    public int getIdOperacion(){
        return idOperacion;
    }

    public float getCantidadPagada(){
        return cantidadPagada;
    }

    public String getFecPago(){
        return fecPago;
    }

    public void setIdPago( int idP){
        idPago = idP;
    }

    public void setIdOperacion( int idO){
        idOperacion = idO;
    }

    public void setCantidadPagada( float caPa){
        cantidadPagada = caPa;
    }

    public void setFecPago( String fcP){
        fecPago = fcP;
    }
}
