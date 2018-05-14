package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;
import com.dragonregnan.sistemasdinamicos.model.usuariosModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 05/01/2016.
 */
public class usuariosDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_UDUARIOS = "usuarios";
    public static final String IDUSUARIO = "idusuario";
    public static final String NOBOLETA = "noboleta";
    public static final String CONTRASENA = "contrasena";
    public static final String NOMBRE = "nombre";
    public static final String APEPATERNO = "apepaterno";
    public static final String APEMATERNO = "apematerno";
    public static final String GRUPO = "grupo";

    public usuariosDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertUsuario(usuariosModel usuario) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(IDUSUARIO, usuario.getIdUsuario());
        cv.put(NOBOLETA, usuario.getNoBoleta());
        cv.put(CONTRASENA, usuario.getContrasena());
        cv.put(NOMBRE, usuario.getNombre());
        cv.put(APEPATERNO, usuario.getApePaterno());
        cv.put(APEMATERNO, usuario.getApeMaterno());
        cv.put(GRUPO, usuario.getGrupo());
        db.insert(TABLE_UDUARIOS, cv);

        db.close();

    }

    public String getContraseña (String boleta){
        String[] fields = {CONTRASENA};
        String condition = NOBOLETA + " = " + boleta ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_UDUARIOS, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(CONTRASENA);
            db.close();
            return cursor.getString(row_id_empresa);
        }else{
            db.close();
            return "vacio";
        }

    }
    public int getIdusuario (String boleta){
        String[] fields = {IDUSUARIO};
        String condition = NOBOLETA + " = " + boleta ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_UDUARIOS, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(IDUSUARIO);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }
    public String getNombre (String boleta){
        String[] fields = {APEPATERNO};
        String condition = NOBOLETA + " = " + boleta ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_UDUARIOS, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(APEPATERNO);
            db.close();
            return cursor.getString(row_id_empresa);
        }else{
            db.close();
            return "vacio";
        }

    }

}
