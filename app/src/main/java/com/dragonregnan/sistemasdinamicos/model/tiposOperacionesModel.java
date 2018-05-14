package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 05/01/2016.
 */
public class tiposOperacionesModel {

    private int idTipoOperacion;

    private String nbTipoOperacion;

    public int getIdTipoOperacion(){
        return idTipoOperacion;
    }

    public String getNbTipoOperacion(){
        return nbTipoOperacion;
    }

    public void setIdTipoOperacion( int idTO){
        idTipoOperacion = idTO;
    }

    public void setNbTipoOperacion( String nbTO){
        nbTipoOperacion = nbTO;
    }
}
