package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.comprasOperacionesModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 04/01/2016.
 */
public class balancesDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_BALANCES = "balances";
    public static final String IDEMPRESA = "idempresa";
    public static final String FECBALANCE = "fecbalance";
    public static final String IDCUENTA = "idcuenta";
    public static final String SALDO = "saldo";

    public balancesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertCuentaBalance (balancesModel balance) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        if(!existsCuenta(balance.getIdEmpresa(),balance.getIdCuenta())){
            cv.put(IDEMPRESA, balance.getIdEmpresa());
            cv.put(IDCUENTA, balance.getIdCuenta());
            cv.put(FECBALANCE,balance.getFecBalance());
            cv.put(SALDO, balance.getSaldo());
            db.insert(TABLE_BALANCES, cv);
            db.close();
        }else{
            cv.put(FECBALANCE,balance.getFecBalance());
            cv.put(SALDO, balance.getSaldo());
            String condition = IDEMPRESA + " = " + balance.getIdEmpresa() + " AND " + IDCUENTA + " = " + balance.getIdCuenta();
            db.update(TABLE_BALANCES, cv, condition);
            db.close();
        }
    }

    public float getSaldo (int idempresa, int idcuenta){
            String[] fields = {SALDO};
            String condition = IDEMPRESA + " = " + idempresa + " AND " + IDCUENTA + " = " + idcuenta;
            try {
                db.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Cursor cursor = db.getData(TABLE_BALANCES, fields, condition);
            if(cursor.getCount() >0){
                cursor.moveToFirst();
                int row_id_empresa = cursor.getColumnIndex(SALDO);
                db.close();
                return cursor.getFloat(row_id_empresa);
            }else{
                db.close();
                return 0;
            }

        }

    public boolean existsCuenta( int idempresa, int idcuenta ){
        boolean exist = false;
        String[] fields = {IDEMPRESA,IDCUENTA};
        String condition = IDEMPRESA + " = " + idempresa + " AND " + IDCUENTA + " = " + idcuenta;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_BALANCES, fields, condition);

        if (cursor.getCount()>0)
            exist = true;
        else
            exist = false;

        cursor.close();
        db.close();
        return exist;
    }

}
