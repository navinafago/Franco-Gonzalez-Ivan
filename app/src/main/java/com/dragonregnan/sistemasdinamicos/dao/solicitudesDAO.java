package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by laura on 04/01/2016.
 */
public class solicitudesDAO {

    private Context context;
    private static DataBaseSource db;

    public static final String  TABLE_SOLICITUDES = "solicitudes";
    public static final String  IDSOLICITUD = "idsolicitud";
    public static final String  IDINDUSTRIA = "idindustria";
    public static final String  FECENTREGASOL = "fecentregasol";
    public static final String  IDEMPRESACOMPRADORA = "idempresacompradora";
    public static final String  CANTSOLICITADA = "cantsolicitada";

    public solicitudesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public static ArrayList<solicitudesModel> getSolicitudes( int IdIndustria){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_SOLICITUDES +
                        " WHERE " + IDINDUSTRIA + " = " + IdIndustria +
                        " ORDER BY " + IDSOLICITUD + " DESC ";

        Cursor cursor = db.getDataRaw(condition, null);
        ArrayList<solicitudesModel> solicitudes = new ArrayList<>();
        solicitudes.clear();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                solicitudesModel solicitud  = new solicitudesModel();

                int row_idsolicitud = cursor.getColumnIndex(IDSOLICITUD);
                int row_idindustria = cursor.getColumnIndex(IDINDUSTRIA);
                int row_fecentregasol = cursor.getColumnIndex(FECENTREGASOL);
                int row_idempresacompradora= cursor.getColumnIndex(IDEMPRESACOMPRADORA);
                int row_cantsolicitada = cursor.getColumnIndex(CANTSOLICITADA);

                solicitud.setIdSolicitud(cursor.getInt(row_idsolicitud));
                solicitud.setIdIndustria(cursor.getInt(row_idindustria));
                solicitud.setFecEntregaSol(cursor.getString(row_fecentregasol));
                solicitud.setIdEmpresaCompradora(cursor.getInt(row_idempresacompradora));
                solicitud.setCantSolicitada(cursor.getInt(row_cantsolicitada));

                solicitudes.add(solicitud);
            }
        }
        db.close();
        return solicitudes;
    }

    public static ArrayList<solicitudesModel> getMisSolicitudes( int IdEmpresa){

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String condition =
                "SELECT * FROM " + TABLE_SOLICITUDES +
                        " WHERE " + IDEMPRESACOMPRADORA + " = " + IdEmpresa +
                        " ORDER BY " + IDSOLICITUD + " DESC ";

        Cursor cursor = db.getDataRaw(condition, null);
        ArrayList<solicitudesModel> solicitudes = new ArrayList<>();
        solicitudes.clear();
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                solicitudesModel solicitud  = new solicitudesModel();

                int row_idsolicitud = cursor.getColumnIndex(IDSOLICITUD);
                int row_idindustria = cursor.getColumnIndex(IDINDUSTRIA);
                int row_fecentregasol = cursor.getColumnIndex(FECENTREGASOL);
                int row_idempresacompradora= cursor.getColumnIndex(IDEMPRESACOMPRADORA);
                int row_cantsolicitada = cursor.getColumnIndex(CANTSOLICITADA);

                solicitud.setIdSolicitud(cursor.getInt(row_idsolicitud));
                solicitud.setIdIndustria(cursor.getInt(row_idindustria));
                int dateColumn = cursor.getColumnIndex("date");
                solicitud.setFecEntregaSol(cursor.getString(row_fecentregasol));
                solicitud.setIdEmpresaCompradora(cursor.getInt(row_idempresacompradora));
                solicitud.setCantSolicitada(cursor.getInt(row_cantsolicitada));

                solicitudes.add(solicitud);
            }
        }
        db.close();
        return solicitudes;
    }

    public static int getIdEmpCompradora( int idSd) {
        String[] fields = {IDEMPRESACOMPRADORA};
        String condition = IDSOLICITUD + " = " + idSd;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_SOLICITUDES, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(IDEMPRESACOMPRADORA);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }

    public static int getIdIndustria( int idSd) {
        String[] fields = {IDINDUSTRIA};
        String condition = IDSOLICITUD + " = " + idSd;

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.getData(TABLE_SOLICITUDES, fields, condition);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            int row_id_empresa = cursor.getColumnIndex(IDINDUSTRIA);
            db.close();
            return cursor.getInt(row_id_empresa);
        }else{
            db.close();
            return 0;
        }

    }
    public void insertSolicitud(solicitudesModel solicitud) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(IDSOLICITUD, solicitud.getIdSolicitud());
        cv.put(IDINDUSTRIA, solicitud.getIdIndustria());
        cv.put(FECENTREGASOL, solicitud.getFecEntregaSol());
        cv.put(IDEMPRESACOMPRADORA, solicitud.getIdEmpresaCompradora());
        cv.put(CANTSOLICITADA, solicitud.getCantSolicitada());
        db.insert(TABLE_SOLICITUDES, cv);

        db.close();

    }

}
