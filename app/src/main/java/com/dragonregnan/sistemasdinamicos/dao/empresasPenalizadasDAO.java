package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.empresasModel;
import com.dragonregnan.sistemasdinamicos.model.empresasPenalizadasModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 10/02/2016.
 */
public class empresasPenalizadasDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLEEMPRESASPENALIZADAS = "empresaspenalizadas";
    public static final String IDEMPRESA = "idempresa";
    public static final String IDREGLA = "idregla";
    public static final String IDEMPRESAVICTIMA = "idempresavictima";

    public empresasPenalizadasDAO (Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertEmpresaPenalizada (empresasPenalizadasModel empresa) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDEMPRESA,empresa.getIdEmpresa());
        cv.put(IDREGLA, empresa.getIdRegla());
        cv.put(IDEMPRESAVICTIMA, empresa.getIdEmpresaVictima());
        db.insert(TABLEEMPRESASPENALIZADAS, cv);
        db.close();

    }

    public int getPenalizaciones( int idEmp){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLEEMPRESASPENALIZADAS +
                        " WHERE " + IDEMPRESA + " = " + idEmp ;

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor.getCount() > 0){

            int cuenta = cursor.getCount();
            db.close();
            return cuenta;
        }else{
        db.close();
        return 0;}
    }

}
