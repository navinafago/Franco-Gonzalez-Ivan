package com.dragonregnan.sistemasdinamicos.dao;

/**
 * Created by laura on 11/01/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.tipoAlmacenModel;
import com.dragonregnan.sistemasdinamicos.model.usuariosEmpresasModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class usuariosEmpresasDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_UDUARIOSEMPRESAS = "usuariosempresas";
    public static final String IDUSUARIO = "idusuario";
    public static final String IDEMPRESA = "idempresa";

    public usuariosEmpresasDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertUsuariosEmpresas (usuariosEmpresasModel usuEmp) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDUSUARIO, usuEmp.getIdUsuario());
        cv.put(IDEMPRESA, usuEmp.getIdEmpresa());
        db.insert(TABLE_UDUARIOSEMPRESAS, cv);

        db.close();
    }
    public int getEmpresa (int usuario){
        String[] fields = {IDEMPRESA};
        String condition = IDUSUARIO + " = " + usuario ;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_UDUARIOSEMPRESAS, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(IDEMPRESA);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }
    }
}

