package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.cuentasModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 04/01/2016.
 */
public class cuentasDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_CUENTAS = "cuentas";
    public static final String IDCUENTA = "idcuenta";
    public static final String NBCUENTA = "nbcuenta";
    public static final String ACREEDORA = "acreedora";

    public cuentasDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertCuenta(cuentasModel cuenta) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDCUENTA, cuenta.getIdCuenta());
        cv.put(NBCUENTA, cuenta.getNbCuenta());
        cv.put(ACREEDORA, cuenta.getAcreedora());
        db.insert(TABLE_CUENTAS, cv);

        db.close();

    }


}
