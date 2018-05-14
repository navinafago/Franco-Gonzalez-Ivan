package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 04/01/2016.
 */
public class comprasDAO {
    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_COMPRAS = "compras";
    public static final String IDCOMPRA = "idcompra";
    public static final String IDCOTIZACION = "idcotizacion";
    public static final String FECCOMPRA = "feccompra";
    public static final String LIQUIDADA = "liquidada";
    public static final String ENTREGADA = "entregada";

    public comprasDAO (Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertCompras(comprasModel compra) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(IDCOMPRA, compra.getIdCompra());
        cv.put(IDCOTIZACION, compra.getIdCotizacion());
        cv.put(FECCOMPRA, compra.getFecCompra());
        int liq = 0;
        if(compra.getLiquidada()==true){
            liq = 1;
        }
        cv.put(LIQUIDADA,liq );
        int ent = 0;
        if(compra.getEntregada()==true){
            ent = 1;
        }
        cv.put(ENTREGADA, ent);
        db.insert(TABLE_COMPRAS, cv);
        db.close();

    }

    public comprasModel getCompra( int IdCotizacion){
        comprasModel compra  = new comprasModel();

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_COMPRAS +
                        " WHERE " + IDCOTIZACION + " = " + IdCotizacion ;

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int row_idcotizacion = cursor.getColumnIndex(IDCOTIZACION);
                int row_feccompra = cursor.getColumnIndex(FECCOMPRA);
                int row_liquidada = cursor.getColumnIndex(LIQUIDADA);
                int row_entregada = cursor.getColumnIndex(ENTREGADA);

                compra.setIdCotizacion(cursor.getInt(row_idcotizacion));
                boolean liquidada = false;
                if(cursor.getInt(row_liquidada)== 1){
                    liquidada = true;
                }
                compra.setLiquidada(liquidada);
                compra.setFecCompra(cursor.getString(row_feccompra));
                boolean entregada = false;
                if(cursor.getInt(row_entregada)== 1){
                    entregada = true;
                }
                compra.setEntregada(entregada);
            }
        }
        db.close();
        return compra;
    }

    public static ArrayList<comprasModel> getcompras(){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_COMPRAS +
                        " WHERE " + LIQUIDADA+ " = " + 1 + " AND " + ENTREGADA + " = " + 1 +
                        " ORDER BY " + IDCOMPRA + " DESC ";

        Cursor cursor = db.getDataRaw(condition, null);
        ArrayList<comprasModel> solicitudes = new ArrayList<>();
        solicitudes.clear();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                comprasModel solicitud  = new comprasModel();

                int row_idsolicitud = cursor.getColumnIndex(IDCOMPRA);
                int row_idindustria = cursor.getColumnIndex(IDCOTIZACION);
                int row_fecentregasol = cursor.getColumnIndex(FECCOMPRA);

                solicitud.setIdCompra(cursor.getInt(row_idsolicitud));
                solicitud.setIdCotizacion(cursor.getInt(row_idindustria));
                solicitud.setFecCompra(cursor.getString(row_fecentregasol));
                solicitudes.add(solicitud);
            }
        }
        db.close();
        return solicitudes;
    }

    public void UpdateLiquidacion( int idCom, int liq) {
        ContentValues cv = new ContentValues();

        cv.put(LIQUIDADA, liq);
        String condition = IDCOMPRA + " = " + idCom;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.update(TABLE_COMPRAS, cv, condition);


        db.close();
    }

    public void UpdateEntregada( int idCom, int ent) {
        ContentValues cv = new ContentValues();

        cv.put(ENTREGADA, ent);
        String condition = IDCOMPRA + " = " + idCom;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.update(TABLE_COMPRAS, cv, condition);


        db.close();
    }

    public boolean existsCompra( int idCoti ){
        boolean exist = false;
        String[] fields = {IDCOMPRA,FECCOMPRA};
        String condition = IDCOTIZACION + " = " + IDCOTIZACION ;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getData(TABLE_COMPRAS, fields, condition);

        if (cursor.getCount()>0)
            exist = true;
        else
            exist = false;

        cursor.close();
        db.close();
        return exist;
    }
}
