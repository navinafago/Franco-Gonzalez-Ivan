package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 04/01/2016.
 */
public class comprasOperacionesModel {

    private int idOperacion;

    private int idTipoOperacion;

    private int idCompra;

    private int idEmpresaCompradora;

    private int idEmpresaVendedora;

    public int getIdOperacion(){
        return idOperacion;
    }

    public int getIdTipoOperacion(){
        return idTipoOperacion;
    }

    public int getIdCompra(){
        return idCompra;
    }

    public int getIdEmpresaCompradora(){
        return idEmpresaCompradora;
    }

    public int getIdEmpresaVendedora(){
        return idEmpresaVendedora;
    }

    public void setIdOperacion( int idOpn){
        idOperacion = idOpn;
    }

    public void setIdTipoOperacion( int idTO){
        idTipoOperacion = idTO;
    }

    public void setIdCompra( int idCm){
        idCompra = idCm;
    }

    public void setIdEmpresaCompradora( int idEC){
        idEmpresaCompradora = idEC;
    }

    public void setIdEmpresaVendedora( int idEV){
        idEmpresaVendedora = idEV;
    }
}
