package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.encadenamientosModel;
import com.dragonregnan.sistemasdinamicos.model.industriasModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 03/01/2016.
 */
public class industriasDAO {

    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_INDUSTRIAS = "industrias";
    public static final String IDINDUSTRIA = "idindustria";
    public static final String NBINDUSTRIA = "nbindustria";

    public industriasDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertIndustria (industriasModel industria) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDINDUSTRIA,industria.getIdIndustria());
        cv.put(NBINDUSTRIA, industria.getNbIndustria());
        db.insert(TABLE_INDUSTRIAS, cv);
        db.close();
    }

}
