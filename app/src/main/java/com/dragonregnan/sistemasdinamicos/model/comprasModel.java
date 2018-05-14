package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 04/01/2016.
 */
public class comprasModel {

    private int idCompra;

    private int idCotizacion;

    private String fecCompra;

    private Boolean liquidada;

    private Boolean entregada;

    public int getIdCompra(){
        return idCompra;
    }

    public int getIdCotizacion(){
        return idCotizacion;
    }

    public String getFecCompra(){
        return fecCompra;
    }

    public  Boolean getLiquidada(){
        return liquidada;
    }

    public Boolean getEntregada(){
        return entregada;
    }

    public void setIdCompra (int idCm){
        idCompra = idCm;
    }

    public void setIdCotizacion( int idCt){
        idCotizacion = idCt;
    }

    public void setFecCompra ( String fcCm){
        fecCompra = fcCm;
    }

    public void setLiquidada( Boolean lq){
        liquidada = lq;
    }

    public void setEntregada( Boolean en){
        entregada = en;
    }
}
