package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 05/01/2016.
 */
public class tipoConfiguracionModel {

    private int idTipoConfiguracion;

    private String nbConfiguracion;

    public int getIdTipoConfiguracion(){
        return idTipoConfiguracion;
    }

    public String getNbConfiguracion(){
        return nbConfiguracion;
    }

    public void setIdTipoConfiguracion( int idTC){
        idTipoConfiguracion = idTC;
    }

    public void setNbConfiguracion( String nbCn){
        nbConfiguracion = nbCn;
    }
}
