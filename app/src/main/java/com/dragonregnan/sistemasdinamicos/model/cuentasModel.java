package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 04/01/2016.
 */
public class cuentasModel {

    private int idCuenta;

    private String nbCuenta;

    private Boolean acreedora;

    public int getIdCuenta(){
        return idCuenta;
    }

    public String getNbCuenta(){
        return nbCuenta;
    }

    public Boolean getAcreedora(){
        return acreedora;
    }

    public void setIdCuenta( int idC){
        idCuenta = idC;
    }

    public void setNbCuenta ( String nbC){
        nbCuenta = nbC;
    }

    public void setAcreedora( Boolean acr){
        acreedora = acr;
    }
}
