package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.almacenesModel;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 03/01/2016.
 */
public class almacenesDAO {
    private Context context;
    private static DataBaseSource db;

    public static final String TABLE_ALMACENES = "almacenes";
    public static final String IDALMACEN = "idalmacen";
    public static final String IDEMPRESA = "idempresa";
    public static final String NBALMACEN = "nbalmacen";
    public static final String IDTIPOALMACEN = "idtipoalmacen";
    public static final String MAXALMACEN = "maxalamcen";

    public almacenesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertAlmacen (almacenesModel almacen) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        if(!existsAlmacen(almacen.getIdAlmacen())){
            cv.put(IDALMACEN,almacen.getIdAlmacen());
            cv.put(IDEMPRESA, almacen.getIdEmpresa());
            cv.put(NBALMACEN, almacen.getNbAlmacen());
            cv.put(IDTIPOALMACEN, almacen.getIdTipoAlmacen());
            cv.put(MAXALMACEN, almacen.getMaxAlmacen());
            db.insert(TABLE_ALMACENES, cv);
        }
    }

    public boolean existsAlmacen( int idAlmacen ){
        boolean exist = false;
        String[] fields = {IDTIPOALMACEN};
        String condition =  IDALMACEN + " = " + idAlmacen;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_ALMACENES, fields, condition);

        if (cursor.getCount()>0)
            exist = true;
        else
            exist = false;

        cursor.close();
        db.close();
        return exist;
    }
    public int getIdAlmacen( int idempresa, int idTipoAlmacen ){
        boolean exist = false;
        String[] fields = {IDALMACEN};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDTIPOALMACEN + " = " + idTipoAlmacen;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_ALMACENES, fields, condition);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(IDALMACEN);
            db.close();
            return cursor.getInt(row_id_empresa);
         }else{
            db.close();
            return 0;
        }
    }

    public int getMaximo( int idempresa, int idTipoAlmacen ){
        boolean exist = false;
        String[] fields = {MAXALMACEN};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDALMACEN + " = " + idTipoAlmacen;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_ALMACENES, fields, condition);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(MAXALMACEN);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }
    }


}
