package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.util.Log;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.nivelesVariablesModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 30/03/2016.
 */
public class ultimasincronizacionDAO {

    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_UDUARIOSEMPRESAS = "ultimasincronizacion";
    public static final String ID = "id";
    public static final String FECHA_SINCRONIZACION = "fecha_sincronizacion";


    public ultimasincronizacionDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }


    public void registrarSincronizacion( String fecha) {
        Log.d("registar sincro",fecha);
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        if(!exists()){
            cv.put(ID, 1);
            cv.put(FECHA_SINCRONIZACION, fecha);
            db.insert(TABLE_UDUARIOSEMPRESAS, cv);

        }else{
            cv.put(FECHA_SINCRONIZACION,fecha);
            String condition = ID + " = " + 1;
            db.update(TABLE_UDUARIOSEMPRESAS, cv, condition);
            db.close();
        }
    }

    public String getSincronizacion (){
        String[] fields = {FECHA_SINCRONIZACION};
        String condition = ID + " = " + 1 ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_UDUARIOSEMPRESAS, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_fecha = cursor.getColumnIndex(FECHA_SINCRONIZACION);
            db.close();
            return cursor.getString(row_id_fecha);
        }else{
            db.close();
            return null;
        }

    }

    public boolean exists(){
        boolean exist = false;
        String[] fields = {FECHA_SINCRONIZACION};
        String condition = ID + " = " + 1 ;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_UDUARIOSEMPRESAS, fields, condition);

        if (cursor.getCount()==1)
            exist = true;
        else
            exist = false;

        cursor.close();
        db.close();
        return exist;
    }
}
