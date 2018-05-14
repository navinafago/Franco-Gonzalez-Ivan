package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 03/01/2016.
 */
public class almacenesModel {

    private int idAlmacen;

    private int idEmpresa;

    private String nbAlmacen;

    private int maxAlmacen;

    private int idTipoAlmacen;

    public int getIdAlmacen(){
        return  idAlmacen;
    }

    public int getIdEmpresa(){
        return idEmpresa;
    }

    public String getNbAlmacen(){
        return nbAlmacen;
    }

    public int getMaxAlmacen(){
        return maxAlmacen;
    }

    public int getIdTipoAlmacen(){
        return idTipoAlmacen;
    }

    public void setIdAlmacen( int idA){
        idAlmacen = idA;
    }

    public void setIdEmpresa( int idE){
        idEmpresa = idE;
    }

    public void setNbAlmacen( String nbA){
        nbAlmacen = nbA;
    }

    public void setMaxAlmacen( int maxA){
        maxAlmacen = maxA;
    }

    public void setIdTipoAlmacen( int idTA){
        idTipoAlmacen = idTA;
    }
}
