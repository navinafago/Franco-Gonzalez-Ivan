package com.dragonregnan.sistemasdinamicos.dao;

import android.content.Context;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;

import java.util.ArrayList;

/**
 * Created by laura on 05/01/2016.
 */
public class configuracionDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_CONFIGURACION = "configuracion";
    public static final String IDUSUARIO = "idusuario";
    public static final String IDTIPOCONFIGURACION = "idtipoconfiguracion";
    public static final String VALOR = "valor";
    public static final String FECMODIFICACION = "fecmodificacion";
}
