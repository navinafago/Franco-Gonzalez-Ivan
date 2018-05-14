package com.dragonregnan.sistemasdinamicos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.db.DataBaseSource;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.comprasOperacionesModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by laura on 04/01/2016.
 */
public class comprasOperacionesDAO {

    private Context context;
    private static DataBaseSource db;
    private ArrayList<Address> addresses;

    public static final String TABLE_COMPRASOPERACIONES = "comprasoperaciones";
    public static final String IDOPERACION = "idoperacion";
    public static final String IDTIPOOPERACION = "idtipooperacion";
    public static final String IDCOMPRA = "idcompra";
    public static final String IDEMPRESACOMPRADORA = "idempresacompradora";
    public static final String IDEMPRESAVENDEDORA = "idempresavendedora";

    public comprasOperacionesDAO(Context context) {
        this.context = context;
        db = new DataBaseSource(context);
    }

    public int insertCompraOperacion(comprasOperacionesModel compOpe) {
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();

        cv.put(IDOPERACION, compOpe.getIdOperacion());
        cv.put(IDTIPOOPERACION, compOpe.getIdTipoOperacion());
        cv.put(IDCOMPRA, compOpe.getIdCompra());
        cv.put(IDEMPRESAVENDEDORA,compOpe.getIdEmpresaVendedora() );
        cv.put(IDEMPRESACOMPRADORA, compOpe.getIdEmpresaCompradora());
        int id = (int) db.insert(TABLE_COMPRASOPERACIONES, cv);

        db.close();

        return id;

    }

    public comprasOperacionesModel getCompraOperacion(int idOpe, int idTipoOpe){
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comprasOperacionesModel compraoperacion = new comprasOperacionesModel();

        String condition =
                "SELECT * FROM " + TABLE_COMPRASOPERACIONES +
                        " WHERE " + IDOPERACION+ " = " + idOpe + " AND " + IDTIPOOPERACION + " = " + idTipoOpe;

        Cursor cursor = db.getDataRaw(condition, null);
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                int row_idCompra = cursor.getColumnIndex(IDCOMPRA);
                int row_idEmpresaCompradora = cursor.getColumnIndex(IDEMPRESACOMPRADORA);
                int row_idEmpresaVendedora = cursor.getColumnIndex(IDEMPRESAVENDEDORA);

                compraoperacion.setIdOperacion(idOpe);
                compraoperacion.setIdTipoOperacion(idTipoOpe);
                compraoperacion.setIdCompra(cursor.getInt(row_idCompra));
                compraoperacion.setIdEmpresaCompradora(cursor.getInt(row_idEmpresaCompradora));
                compraoperacion.setIdEmpresaVendedora(cursor.getInt(row_idEmpresaVendedora));
            }
        }
        db.close();
        return compraoperacion;
    }

}
