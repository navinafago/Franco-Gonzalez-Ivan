package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 10/02/2016.
 */
public class empresasPenalizadasModel {

    private int idEmpresa;
    private int idRegla;
    private int idEmpresaVictima;

    public int getIdEmpresa(){
        return idEmpresa;
    }
    public int getIdRegla(){
        return idRegla;
    }
    public int getIdEmpresaVictima(){
        return idEmpresaVictima;
    }

    public void setIdEmpresa( int idEs){
        idEmpresa=idEs;
    }
    public void setIdRegla ( int idRl){
        idRegla = idRl;
    }
    public void setIdEmpresaVictima( int idEsVm){
        idEmpresaVictima = idEsVm;
    }

}
