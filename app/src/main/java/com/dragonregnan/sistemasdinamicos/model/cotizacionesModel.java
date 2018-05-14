package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 04/01/2016.
 */
public class cotizacionesModel {

    private int idCotizacion;

    private int idSolicitud;

    private int cantOfrecida;

    private float precio;

    private String fecExpiracion;

    private String fecEntrega;

    private int estado;

    private int idEmpresaVendedora;

    public int getIdCotizacion(){
        return idCotizacion;
    }

    public int getIdSolicitud(){
        return idSolicitud;
    }

    public int getCantOfrecida(){
        return cantOfrecida;
    }

    public float getPrecio(){
        return precio;
    }

    public String getFecExpiracion(){
        return fecExpiracion;
    }

    public String getFecEntrega(){
        return fecEntrega;
    }

    public int getEstado(){
        return estado;
    }

    public int getIdEmpresaVendedora(){
        return idEmpresaVendedora;
    }

    public void setIdCotizacion( int idCot){
        idCotizacion = idCot;
    }

    public void setIdSolicitud( int idSol){
        idSolicitud = idSol;
    }

    public void setCantOfrecida( int caof){
        cantOfrecida = caof;
    }

    public void setPrecio( float  prc){
        precio = prc;
    }

    public void setFecExpiracion( String fcEx){
        fecExpiracion = fcEx;
    }

    public void setFecEntrega( String fcEn){
        fecEntrega = fcEn;
    }

    public void setEstado( int ed){
        estado=ed;
    }

    public void setIdEmpresaVendedora( int idEmVn){
        idEmpresaVendedora = idEmVn;
    }

}
