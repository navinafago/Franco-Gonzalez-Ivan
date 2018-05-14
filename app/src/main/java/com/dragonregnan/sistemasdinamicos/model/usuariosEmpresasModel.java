package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 11/01/2016.
 */
public class usuariosEmpresasModel {

    private int idUsuario;
    private int idEmpresa;

    public int getIdUsuario(){
        return idUsuario;
    }
    public int getIdEmpresa(){
        return idEmpresa;
    }
    public void setIdUsuario( int idUr){
        idUsuario = idUr;
    }
    public void setIdEmpresa( int idEs){
        idEmpresa=idEs;
    }
}
