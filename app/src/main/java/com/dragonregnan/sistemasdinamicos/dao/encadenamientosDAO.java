package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.util.Log;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.empresasModel;
import com.dragonregnan.sistemasdinamicos.model.encadenamientosModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 03/01/2016.
 */
public class encadenamientosDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_ENCADENAMIENTOS = "encadenamientos";
    public static final String IDINDUSTRIACOMPRADORA = "idindustriacompradora";
    public static final String IDINDUSTRIAVENDEDORA = "idindustriavendedora";
    public static final String COEFICIENTE = "coeficiente";

    public encadenamientosDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertEncadenamiento (encadenamientosModel encad) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDINDUSTRIACOMPRADORA,encad.getIdIndustriaCompradora());
        cv.put(IDINDUSTRIAVENDEDORA, encad.getIdIndustriaVendedora());
        cv.put(COEFICIENTE, encad.getCoeficiente());
        db.insert(TABLE_ENCADENAMIENTOS, cv);
        db.close();
    }

    public static ArrayList<Integer> getIndustriasVenden( int idIndustriaCompradora){
        String[] fields = {IDINDUSTRIAVENDEDORA };
        String condition = IDINDUSTRIACOMPRADORA + " = " + idIndustriaCompradora;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_ENCADENAMIENTOS, fields, condition);
        ArrayList<Integer> vendedoras = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int row_idsolicitud = cursor.getColumnIndex(IDINDUSTRIAVENDEDORA);

                vendedoras.add(cursor.getInt(row_idsolicitud));
                Log.d("vendedoras",String.valueOf(cursor.getInt(row_idsolicitud)));
            }
        }
        db.close();
        return vendedoras;
    }
    public static int getCoeficiente( int idIndustriaCompradora, int idIndustriaVendedora){
        int row_idsolicitud =0;
        String[] fields = {COEFICIENTE};
        String condition = IDINDUSTRIACOMPRADORA + " = " + idIndustriaCompradora + " AND " + IDINDUSTRIAVENDEDORA + " = " + idIndustriaVendedora;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor =  db.getData(TABLE_ENCADENAMIENTOS, fields, condition);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToNext();
                row_idsolicitud = cursor.getColumnIndex(COEFICIENTE);

        }
        db.close();
        return cursor.getInt(row_idsolicitud);
    }
}
