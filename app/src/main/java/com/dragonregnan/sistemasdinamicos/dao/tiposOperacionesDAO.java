package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.tipoAlmacenModel;
import com.dragonregnan.sistemasdinamicos.model.tiposOperacionesModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 05/01/2016.
 */
public class tiposOperacionesDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String  TABLE_TIPOOPERACIONES = "tipooperaciones";
    public static final String  IDTIPOOPERACION = "idtipooperaciones";
    public static final String  NBTIPOOPERACION = "nbtipooperaciones";

    public tiposOperacionesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertTipoAlmacen(tiposOperacionesModel TipoOpe) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDTIPOOPERACION, TipoOpe.getIdTipoOperacion());
        cv.put(NBTIPOOPERACION, TipoOpe.getNbTipoOperacion());
        db.insert(TABLE_TIPOOPERACIONES, cv);

        db.close();

    }
}
