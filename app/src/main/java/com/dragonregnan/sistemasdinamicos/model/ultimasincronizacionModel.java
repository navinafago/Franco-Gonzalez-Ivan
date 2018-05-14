package com.dragonregnan.sistemasdinamicos.model;

import java.sql.Date;

/**
 * Created by laura on 30/03/2016.
 */
public class ultimasincronizacionModel {

    private String fecha_sincronizacion;

    public String getFecha_sincronizacion(){
        return fecha_sincronizacion;
    }

    public void setFecha_sincronizacion( String fecSinc){
        fecha_sincronizacion = fecSinc;
}
}
