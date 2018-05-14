package com.dragonregnan.sistemasdinamicos.dao;

import android.content.Context;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;

import java.util.ArrayList;

/**
 * Created by laura on 08/02/2016.
 */
public class reglascompensacionDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLEREGLASCOMPENSACION = "reglascompesacion";
    public static final String IDREGLA = "idregla";
    public static final String NBREGLA = "nbregla";
}
