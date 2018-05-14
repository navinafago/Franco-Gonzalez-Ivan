package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 03/01/2016.
 */
public class tipoAlmacenModel {

    private int idTipoAlmacen;

    private String nbTipoAlmacen;

    public int getIdTipoAlmacen(){
        return  idTipoAlmacen;
    }

    public String getNbTipoAlmacen(){
        return nbTipoAlmacen;
    }

    public void setIdTipoAlmacen( int idTA){
        idTipoAlmacen = idTA;
    }

    public void setNbTipoAlmacen( String nbTA){
        nbTipoAlmacen = nbTA;
    }
}
