package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 04/01/2016.
 */
public class solicitudesModel {

    private int idSolicitud;

    private int idIndustria;

    private String fecEntregaSol;

    private int idEmpresaCompradora;

    private int cantSolicitada;

    public int getIdSolicitud(){
        return idSolicitud;
    }

    public int getIdIndustria(){
        return idIndustria;
    }

    public String getFecEntregaSol(){
        return fecEntregaSol;
    }

    public int getIdEmpresaCompradora(){
        return idEmpresaCompradora;
    }

    public int getCantSolicitada(){
        return cantSolicitada;
    }

    public void setIdSolicitud( int idS){
        idSolicitud = idS;
    }

    public void setIdIndustria( int idI){
        idIndustria= idI;
    }

    public void setFecEntregaSol( String fecES){
        fecEntregaSol = fecES;
    }

    public void setIdEmpresaCompradora( int idEC){
        idEmpresaCompradora = idEC;
    }

    public void setCantSolicitada( int ctSd){
        cantSolicitada=ctSd;
    }

}
