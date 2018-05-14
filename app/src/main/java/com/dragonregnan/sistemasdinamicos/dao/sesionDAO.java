package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.sesionModel;
import com.dragonregnan.sistemasdinamicos.model.usuariosModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 05/01/2016.
 */
public class sesionDAO {

    private Context context;
    private static DataBaseSource db;

    public static final String TABLE_SESION = "sesion";
    public static final String IDSESION = "idsesion";
    public static final String IDUSUARIO = "idusuario";
    public static final String ACTIVA = "activa";

    public sesionDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertSesion(sesionModel sesion) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(IDUSUARIO, sesion.getIdUsuario());
        cv.put(ACTIVA, sesion.getActiva());
        db.insert(TABLE_SESION, cv);

        db.close();

    }

    public int getActiva (){
        String[] fields = {IDUSUARIO};
        String condition = ACTIVA + " = " + 1 ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_SESION, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_usuario = cursor.getColumnIndex(IDUSUARIO);
            db.close();
            return cursor.getInt(row_id_usuario);
        }else{
            db.close();
            return 0;
        }
    }
}
