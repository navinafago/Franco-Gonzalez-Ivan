package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 04/01/2016.
 */
public class balancesModel {

    private int idEmpresa;

    private String fecBalance;

    private int idCuenta;

    private float saldo;

    public int getIdEmpresa(){
        return idEmpresa;
    }

    public String getFecBalance(){
        return fecBalance;
    }

    public int getIdCuenta(){
        return idCuenta;
    }

    public float getSaldo(){
        return saldo;
    }

    public void setIdEmpresa( int idE){
        idEmpresa = idE;
    }

    public void setIdCuenta( int idC){
        idCuenta = idC;
    }

    public void setFecBalance ( String fecB){
        fecBalance = fecB;
    }

    public void setSaldo ( float sal){
        saldo = sal;
    }

}
