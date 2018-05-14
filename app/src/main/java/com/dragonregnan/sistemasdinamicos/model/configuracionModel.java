package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 05/01/2016.
 */
public class configuracionModel {

    private int idUsuario;

    private int idTipoConfiguracion;

    private int valor;

    private Date fecModificacion;

    public int getIdUsuario(){
        return idUsuario;
    }

    public int getIdTipoConfiguracion(){
        return idTipoConfiguracion;
    }

    public int getValor(){
        return valor;
    }

    public Date getFecModificacion(){
        return fecModificacion;
    }

    public void setIdUsuario( int idUr){
        idUsuario = idUr;
    }

    public void setIdTipoConfiguracion( int idTC){
        idTipoConfiguracion = idTC;
    }

    public void setValor( int vr){
        valor = vr;
    }

    public void setFecModificacion( Date fecMn){
        fecModificacion = fecMn;
    }
}
