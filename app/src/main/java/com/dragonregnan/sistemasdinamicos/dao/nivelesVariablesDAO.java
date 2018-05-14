package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.nivelesVariablesModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 05/01/2016.
 */
public class nivelesVariablesDAO {

    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_NIVELESVARIABLES = "nivelesvariables";
    public static final String IDEMPRESA = "idempresa";
    public static final String IDALMACEN = "idalmacen";
    public static final String DESEADO = "deseado";
    public static final String ACTUAL = "actual";
    public static final String MINIMODESEADO = "minimodeseado";

    public nivelesVariablesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }
    public void insertNivel(nivelesVariablesModel nivel) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        if(!existsNivel(nivel.getIdEmpresa(), nivel.getIdAlmacen())){
            cv.put(IDEMPRESA, nivel.getIdEmpresa());
            cv.put(IDALMACEN, nivel.getIdAlmacen());
            cv.put(DESEADO,nivel.getDeseado() );
            cv.put(ACTUAL, nivel.getActual());
            cv.put(MINIMODESEADO, nivel.getMinimoDeseado());
            db.insert(TABLE_NIVELESVARIABLES, cv);

        }else{
            cv.put(DESEADO,nivel.getDeseado());
            cv.put(ACTUAL, nivel.getActual());
            cv.put(MINIMODESEADO, nivel.getMinimoDeseado());
            String condition = IDEMPRESA + " = " + nivel.getIdEmpresa() + " AND " + IDALMACEN + " = " + nivel.getIdAlmacen();
            db.update(TABLE_NIVELESVARIABLES, cv, condition);
            db.close();
        }
    }
    public static nivelesVariablesModel getNivel( int IdEmpresa, int IdAlmacen){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_NIVELESVARIABLES +
                        " WHERE " + IDEMPRESA + " = " + IdEmpresa + " AND " + IDALMACEN + " = " + IdAlmacen;
        nivelesVariablesModel nivel  = new nivelesVariablesModel();

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor != null && cursor.getCount() > 0){

                int row_idsolicitud = cursor.getColumnIndex(IDEMPRESA);
                int row_idindustria = cursor.getColumnIndex(IDALMACEN);
                int row_fecentregasol = cursor.getColumnIndex(DESEADO);
                int row_idempresacompradora= cursor.getColumnIndex(ACTUAL);
                int row_cantsolicitada = cursor.getColumnIndex(MINIMODESEADO);

            nivel.setIdEmpresa(cursor.getInt(row_idsolicitud));
            nivel.setIdAlmacen(cursor.getInt(row_idindustria));
            nivel.setDeseado(row_fecentregasol);
            nivel.setActual(row_idempresacompradora);
            nivel.setDeseado(cursor.getInt(row_cantsolicitada));

        }
        db.close();
        return nivel;
    }
    public int getDeseaso (int idempresa, int idalmacen){
        String[] fields = {DESEADO};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDALMACEN + " = " + idalmacen;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_NIVELESVARIABLES, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(DESEADO);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }
    public int getActual (int idempresa, int idalmacen){
        String[] fields = {ACTUAL};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDALMACEN + " = " + idalmacen;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_NIVELESVARIABLES, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(ACTUAL);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }
    public int getMinimoDeseado (int idempresa, int idalmacen){
        String[] fields = {MINIMODESEADO};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDALMACEN + " = " + idalmacen;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_NIVELESVARIABLES, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(MINIMODESEADO);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }

    public boolean existsNivel( int idempresa, int idalmacen ){
        boolean exist = false;
        String[] fields = {IDEMPRESA,IDALMACEN};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDALMACEN + " = " + idalmacen;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_NIVELESVARIABLES, fields, condition);

        if (cursor.getCount()>0)
            exist = true;
        else
            exist = false;

        cursor.close();
        db.close();
        return exist;
    }
}
