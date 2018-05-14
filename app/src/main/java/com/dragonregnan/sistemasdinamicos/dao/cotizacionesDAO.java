package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.util.Log;
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
public class cotizacionesDAO {
    private Context context;
    private static DataBaseSource db;

    public static final String TABLE_COTIZACIONES = "cotizaciones";
    public static final String IDCOTIZACION = "idcotizacion";
    public static final String IDSOLICITUD = "idsolicitud";
    public static final String CANTOFRECIDA = "cantofrecida";
    public static final String PRECIO = "precio";
    public static final String FECEXPIRACION = "fecexpiracion";
    public static final String FECENTREGA = "fecentrega";
    public static final String ESTADO = "estado";
    public static final String IDEMPRESAVENDEDORA = "idempresavendedora";

    public cotizacionesDAO (Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public void insertCotizacion(cotizacionesModel cotizacion) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put(IDCOTIZACION, cotizacion.getIdCotizacion());
        cv.put(IDSOLICITUD, cotizacion.getIdSolicitud());
        cv.put(CANTOFRECIDA, cotizacion.getCantOfrecida());
        cv.put(PRECIO, cotizacion.getPrecio());
        cv.put(FECEXPIRACION, cotizacion.getFecExpiracion());
        cv.put(FECENTREGA, cotizacion.getFecEntrega());
        cv.put(ESTADO, cotizacion.getEstado());
        cv.put(IDEMPRESAVENDEDORA, cotizacion.getIdEmpresaVendedora());
        db.insert(TABLE_COTIZACIONES, cv);

        db.close();

    }
    public void updateCotizacion(int idCotizacion, int estado) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(ESTADO, estado);
        String condition = IDCOTIZACION + " = " + idCotizacion;
        db.update(TABLE_COTIZACIONES, cv, condition);

        db.close();
    }
    public static ArrayList<cotizacionesModel> getCotizaciones( int IdEmpresa){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_COTIZACIONES +
                        " WHERE " + IDEMPRESAVENDEDORA + " = " + IdEmpresa +
                        " ORDER BY " + IDCOTIZACION + " DESC ";

        Cursor cursor = db.getDataRaw(condition, null);
        ArrayList<cotizacionesModel> cotizaciones = new ArrayList<>();
        cotizaciones.clear();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                cotizacionesModel cot  = new cotizacionesModel();

                int row_idcotizacion = cursor.getColumnIndex(IDCOTIZACION);
                int row_idsolicitud = cursor.getColumnIndex(IDSOLICITUD);
                int row_cantofrecida = cursor.getColumnIndex(CANTOFRECIDA);
                int row_precio = cursor.getColumnIndex(PRECIO);
                int row_fecexpiracion = cursor.getColumnIndex(FECEXPIRACION);
                int row_fecentrega = cursor.getColumnIndex(FECENTREGA);
                int row_estado = cursor.getColumnIndex(ESTADO);
                int row_idempresavendedora= cursor.getColumnIndex(IDEMPRESAVENDEDORA);

                cot.setIdCotizacion(cursor.getInt(row_idcotizacion));
                cot.setIdSolicitud(cursor.getInt(row_idsolicitud));
                cot.setCantOfrecida(cursor.getInt(row_cantofrecida));
                cot.setPrecio(cursor.getFloat(row_precio));
                cot.setFecExpiracion(cursor.getString(row_fecexpiracion));
                cot.setFecEntrega(cursor.getString(row_fecentrega));
                cot.setEstado(cursor.getInt(row_estado));
                cot.setIdEmpresaVendedora(cursor.getInt(row_idempresavendedora));

                cotizaciones.add(cot);
            }
        }
        db.close();
        return cotizaciones;
    }
    public static ArrayList<cotizacionesModel> getCotizacionesSolicitud( int IdSolicitud){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_COTIZACIONES +
                        " WHERE " + IDSOLICITUD + " = " + IDSOLICITUD +
                        " ORDER BY " + IDCOTIZACION + " DESC ";

        Cursor cursor = db.getDataRaw(condition, null);
        ArrayList<cotizacionesModel> cotizaciones = new ArrayList<>();
        cotizaciones.clear();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                cotizacionesModel cot  = new cotizacionesModel();

                int row_idcotizacion = cursor.getColumnIndex(IDCOTIZACION);
                int row_idsolicitud = cursor.getColumnIndex(IDSOLICITUD);
                int row_cantofrecida = cursor.getColumnIndex(CANTOFRECIDA);
                int row_precio = cursor.getColumnIndex(PRECIO);
                int row_fecexpiracion = cursor.getColumnIndex(FECEXPIRACION);
                int row_fecentrega = cursor.getColumnIndex(FECENTREGA);
                int row_estado = cursor.getColumnIndex(ESTADO);
                int row_idempresavendedora= cursor.getColumnIndex(IDEMPRESAVENDEDORA);

                cot.setIdCotizacion(cursor.getInt(row_idcotizacion));
                cot.setIdSolicitud(cursor.getInt(row_idsolicitud));
                cot.setCantOfrecida(cursor.getInt(row_cantofrecida));
                cot.setPrecio(cursor.getFloat(row_precio));
                cot.setFecExpiracion(cursor.getString(row_fecexpiracion));
                cot.setFecEntrega(cursor.getString(row_fecentrega));
                cot.setEstado(cursor.getInt(row_estado));
                cot.setIdEmpresaVendedora(cursor.getInt(row_idempresavendedora));

                cotizaciones.add(cot);
            }
        }
        db.close();
        return cotizaciones;
    }
    public cotizacionesModel getCotizacion( int idcot){
        cotizacionesModel cot  = new cotizacionesModel();

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_COTIZACIONES +
                        " WHERE " + IDSOLICITUD + " = " + idcot;

        Cursor cursor = db.getDataRaw(condition, null);

        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){


                int row_idcotizacion = cursor.getColumnIndex(IDCOTIZACION);
                int row_idsolicitud = cursor.getColumnIndex(IDSOLICITUD);
                int row_cantofrecida = cursor.getColumnIndex(CANTOFRECIDA);
                int row_precio = cursor.getColumnIndex(PRECIO);
                int row_fecexpiracion = cursor.getColumnIndex(FECEXPIRACION);
                int row_fecentrega = cursor.getColumnIndex(FECENTREGA);
                int row_estado = cursor.getColumnIndex(ESTADO);
                int row_idempresavendedora= cursor.getColumnIndex(IDEMPRESAVENDEDORA);

                cot.setIdCotizacion(cursor.getInt(row_idcotizacion));
                cot.setIdSolicitud(cursor.getInt(row_idsolicitud));
                cot.setCantOfrecida(cursor.getInt(row_cantofrecida));
                cot.setPrecio(cursor.getFloat(row_precio));
                cot.setFecExpiracion(cursor.getString(row_fecexpiracion));
                cot.setFecEntrega(cursor.getString(row_fecentrega));
                cot.setEstado(cursor.getInt(row_estado));
                cot.setIdEmpresaVendedora(cursor.getInt(row_idempresavendedora));

            }
        }
        db.close();
        return cot;
    }

    public int getIdSolicitud( int IdCotizacion ){
        int idSolicitud = 0;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String condition =
                "SELECT " + IDSOLICITUD + "FROM " + TABLE_COTIZACIONES +
                        " WHERE " + IDCOTIZACION + " = " + IdCotizacion ;

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int row_idcotizacion = cursor.getColumnIndex(IDSOLICITUD);

               idSolicitud = cursor.getInt(row_idcotizacion);

            }
        }
        db.close();
        return idSolicitud;
    }
    public int getIdEmpresaVendedora( int IdCotizacion ){
        int idEmpresaVendedora = 0;
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String condition =
                "SELECT " + IDEMPRESAVENDEDORA + "FROM " + TABLE_COTIZACIONES +
                        " WHERE " + IDCOTIZACION + " = " + IdCotizacion ;

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int row_idcotizacion = cursor.getColumnIndex(IDEMPRESAVENDEDORA);

                idEmpresaVendedora = cursor.getInt(row_idcotizacion);

            }
        }
        db.close();
        return idEmpresaVendedora;
    }

}
