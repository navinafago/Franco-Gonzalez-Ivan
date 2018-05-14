package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.cuentasModel;
import com.dragonregnan.sistemasdinamicos.model.tipoAlmacenModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 03/01/2016.
 */
public class tipoAlmacenDAO {

    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String  TABLE_TIPOALMACEN = "tipoalmacen";
    public static final String  IDTIPOALMACEN = "idtipoalmacen";
    public static final String  NBTIPOALMACEN = "nbtipoalmacen";

    public tipoAlmacenDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertTipoAlmacen(tipoAlmacenModel TipoAlma) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDTIPOALMACEN, TipoAlma.getIdTipoAlmacen());
        cv.put(NBTIPOALMACEN, TipoAlma.getNbTipoAlmacen());
        db.insert(TABLE_TIPOALMACEN, cv);

        db.close();

    }
}
