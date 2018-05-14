package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 03/01/2016.
 */
public class encadenamientosModel {

    private int idIndustriaCompradora;

    private int idIndustriaVendedora;

    private int coeficiente;

    public int getIdIndustriaCompradora(){
        return  idIndustriaCompradora;
    }

    public int getIdIndustriaVendedora(){
        return  idIndustriaVendedora;
    }

    public  int getCoeficiente(){
        return coeficiente;
    }

    public void setIdIndustriaCompradora( int idIC){
        idIndustriaCompradora = idIC;
    }

    public void setIdIndustriaVendedora( int idIV){
        idIndustriaVendedora = idIV;
    }

    public void setCoeficiente( int cof){
        coeficiente = cof;
    }

}
