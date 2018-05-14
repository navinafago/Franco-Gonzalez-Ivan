package com.dragonregnan.sistemasdinamicos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dragonregnan.sistemasdinamicos.dao.*;


/**
 * Created by josh on 06/07/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper sInstance;
    public static final String dataBaseName = "tesis.db";
    private static final int dataBaseVersion = 1;

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DataBaseHelper(context.getApplicationContext());
        return sInstance;
    }

    private static final String CREATE_TABLE_EMPRESAS =
            "CREATE TABLE " + empresasDAO.TABLE_EMPRESAS + " ("
                    + empresasDAO.IDEMPRESA + " TEXT PRIMARY KEY, "
                    + empresasDAO.NBEMPRESA + " TEXT, "
                    + empresasDAO.IDINDUSTRIA + " INTEGER, "
                    + "FOREIGN KEY " + "("+empresasDAO.IDINDUSTRIA+") REFERENCES " + industriasDAO.TABLE_INDUSTRIAS + "("+ industriasDAO.IDINDUSTRIA +"));";

    private static final String CREATE_TABLE_INDUSTRIAS =
            "CREATE TABLE " + industriasDAO.TABLE_INDUSTRIAS + " ("
                    + industriasDAO.IDINDUSTRIA  + " INTEGER PRIMARY KEY, "
                    + industriasDAO.NBINDUSTRIA + " TEXT);";
    private static final String CREATE_TABLE_ALAMCENES =
            "CREATE TABLE " + almacenesDAO.TABLE_ALMACENES + " ("
                    + almacenesDAO.IDALMACEN + " TEXT PRIMARY KEY, "
                    + almacenesDAO.IDEMPRESA + " INTEGER, "
                    + almacenesDAO.NBALMACEN + " TEXT, "
                    + almacenesDAO.MAXALMACEN + " INTEGER, "
                    + almacenesDAO.IDTIPOALMACEN + " INTEGER, "
                    + "FOREIGN KEY " + "("+almacenesDAO.IDEMPRESA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"),"
                    + "FOREIGN KEY " + "("+almacenesDAO.IDTIPOALMACEN+") REFERENCES " + tipoAlmacenDAO.TABLE_TIPOALMACEN + "("+ tipoAlmacenDAO.IDTIPOALMACEN +"));";
    private static final String CREATE_TABLE_TIPOALMACEN =
            "CREATE TABLE " + tipoAlmacenDAO.TABLE_TIPOALMACEN + " ("
                    + tipoAlmacenDAO.IDTIPOALMACEN  + " INTEGER PRIMARY KEY, "
                    + tipoAlmacenDAO.NBTIPOALMACEN + " TEXT);";
    private static final String CREATE_TABLE_ENCADENAMIENTOS =
            "CREATE TABLE " + encadenamientosDAO.TABLE_ENCADENAMIENTOS + " ("
                    + encadenamientosDAO.IDINDUSTRIACOMPRADORA + " INTEGER, "
                    + encadenamientosDAO.IDINDUSTRIAVENDEDORA + " INTEGER, "
                    + encadenamientosDAO.COEFICIENTE + " INTEGER, "
                    + "FOREIGN KEY " + "("+encadenamientosDAO.IDINDUSTRIACOMPRADORA+") REFERENCES " + industriasDAO.TABLE_INDUSTRIAS + "("+ industriasDAO.IDINDUSTRIA +"),"
                    + "FOREIGN KEY " + "("+encadenamientosDAO.IDINDUSTRIAVENDEDORA+") REFERENCES " + industriasDAO.TABLE_INDUSTRIAS + "("+ industriasDAO.IDINDUSTRIA +"));";
    private static final String CREATE_TABLE_BALANCES =
            "CREATE TABLE " + balancesDAO.TABLE_BALANCES + " ("
                    + balancesDAO.IDEMPRESA + " INTEGER, "
                    + balancesDAO.FECBALANCE + " TEXT, "
                    + balancesDAO.IDCUENTA + " INTEGER, "
                    + balancesDAO.SALDO + " INTEGER, "
                    + "FOREIGN KEY " + "("+balancesDAO.IDEMPRESA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"),"
                    + "FOREIGN KEY " + "("+balancesDAO.IDCUENTA+") REFERENCES " + cuentasDAO.TABLE_CUENTAS + "("+ cuentasDAO.IDCUENTA +"));";
    private static final String CREATE_TABLE_CUENTAS =
            "CREATE TABLE " + cuentasDAO.TABLE_CUENTAS + " ("
                    + cuentasDAO.IDCUENTA + " INTEGER PRIMARY KEY,  "
                    + cuentasDAO.NBCUENTA + " TEXT, "
                    + cuentasDAO.ACREEDORA + " INTEGER);";
    private static final String CREATE_TABLE_SOLICITUDES =
            "CREATE TABLE " + solicitudesDAO.TABLE_SOLICITUDES + " ("
                    + solicitudesDAO.IDSOLICITUD + " INTEGER PRIMARY KEY, "
                    + solicitudesDAO.IDINDUSTRIA + " TEXT, "
                    + solicitudesDAO.CANTSOLICITADA + " INTEGER, "
                    + solicitudesDAO.FECENTREGASOL + " INTEGER, "
                    + solicitudesDAO.IDEMPRESACOMPRADORA + " INTEGER, "
                    + "FOREIGN KEY " + "("+solicitudesDAO.IDINDUSTRIA+") REFERENCES " + industriasDAO.TABLE_INDUSTRIAS + "("+ industriasDAO.IDINDUSTRIA +"),"
                    + "FOREIGN KEY " + "("+solicitudesDAO.IDEMPRESACOMPRADORA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"));";
    private static final String CREATE_TABLE_COTIZACIONES =
            "CREATE TABLE " + cotizacionesDAO.TABLE_COTIZACIONES + " ("
                    + cotizacionesDAO.IDCOTIZACION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + cotizacionesDAO.IDSOLICITUD + " INTEGER, "
                    + cotizacionesDAO.CANTOFRECIDA + " INTEGER, "
                    + cotizacionesDAO.PRECIO + " REAL, "
                    + cotizacionesDAO.FECEXPIRACION + " TEXT, "
                    + cotizacionesDAO.FECENTREGA + " TEXT, "
                    + cotizacionesDAO.ESTADO + " INTEGER, "
                    + cotizacionesDAO.IDEMPRESAVENDEDORA + " INTEGER, "
                    + "FOREIGN KEY " + "("+cotizacionesDAO.IDSOLICITUD+") REFERENCES " + solicitudesDAO.TABLE_SOLICITUDES + "("+ solicitudesDAO.IDSOLICITUD +"),"
                    + "FOREIGN KEY " + "("+cotizacionesDAO.IDEMPRESAVENDEDORA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"));";
    private static final String CREATE_TABLE_COMPRAS =
            "CREATE TABLE " + comprasDAO.TABLE_COMPRAS + " ("
                    + comprasDAO.IDCOMPRA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + comprasDAO.IDCOTIZACION + " INTEGER, "
                    + comprasDAO.FECCOMPRA + " TEXT, "
                    + comprasDAO.LIQUIDADA + " INTEGER, "
                    + comprasDAO.ENTREGADA + " INTEGER, "
                    + "FOREIGN KEY " + "("+comprasDAO.IDCOTIZACION+") REFERENCES " + cotizacionesDAO.TABLE_COTIZACIONES + "("+ cotizacionesDAO.IDCOTIZACION +"));";
    private static final String CREATE_TABLE_COMPRASOPERACIONES =
            "CREATE TABLE " + comprasOperacionesDAO.TABLE_COMPRASOPERACIONES + " ("
                    + comprasOperacionesDAO.IDOPERACION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + comprasOperacionesDAO.IDTIPOOPERACION + " INTEGER, "
                    + comprasOperacionesDAO.IDCOMPRA + " INTEGER, "
                    + comprasOperacionesDAO.IDEMPRESACOMPRADORA + " INTEGER, "
                    + comprasOperacionesDAO.IDEMPRESAVENDEDORA + " INTEGER, "
                    + "FOREIGN KEY " + "("+comprasOperacionesDAO.IDTIPOOPERACION+") REFERENCES " + tiposOperacionesDAO.TABLE_TIPOOPERACIONES + "("+ tiposOperacionesDAO.IDTIPOOPERACION +"),"
                    + "FOREIGN KEY " + "("+comprasOperacionesDAO.IDCOMPRA+") REFERENCES " + comprasDAO.TABLE_COMPRAS + "("+ comprasDAO.IDCOMPRA +"),"
                    + "FOREIGN KEY " + "("+comprasOperacionesDAO.IDEMPRESACOMPRADORA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"),"
                    + "FOREIGN KEY " + "("+comprasOperacionesDAO.IDEMPRESAVENDEDORA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"));";
    private static final String CREATE_TABLE_PAGOS =
            "CREATE TABLE " + pagosDAO.TABLE_PAGOS + " ("
                    + pagosDAO.IDPAGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + pagosDAO.IDOPERACION + " INTEGER, "
                    + pagosDAO.CANTIDADPAGADA + " REAL, "
                    + "FOREIGN KEY " + "("+pagosDAO.IDOPERACION+") REFERENCES " + comprasOperacionesDAO.TABLE_COMPRASOPERACIONES + "("+ comprasOperacionesDAO.IDOPERACION +"));";
    private static final String CREATE_TABLE_EMBARQUES =
            "CREATE TABLE " + embarquesDAO.TABLE_EMBARQUES + " ("
                    + embarquesDAO.IDEMBARQUE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + embarquesDAO.IDOPERACION + " INTEGER, "
                    + embarquesDAO.CANTIDADEMBARCADA + " INTEGER, "
                    + embarquesDAO.FECEMBARQUE + " TEXT, "
                    + "FOREIGN KEY " + "("+embarquesDAO.IDOPERACION+") REFERENCES " + comprasOperacionesDAO.TABLE_COMPRASOPERACIONES + "("+ comprasOperacionesDAO.IDOPERACION +"));";
    private static final String CREATE_TABLE_TIPOSOPERACIONES =
            "CREATE TABLE " + tiposOperacionesDAO.TABLE_TIPOOPERACIONES + " ("
                    + tiposOperacionesDAO.IDTIPOOPERACION  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tiposOperacionesDAO.NBTIPOOPERACION + " TEXT);";
    private static final String CREATE_TABLE_NIVELESVARIABLES =
            "CREATE TABLE " + nivelesVariablesDAO.TABLE_NIVELESVARIABLES + " ("
                    + nivelesVariablesDAO.IDEMPRESA + " INTEGER, "
                    + nivelesVariablesDAO.IDALMACEN + " INTEGER, "
                    + nivelesVariablesDAO.DESEADO + " INTEGER, "
                    + nivelesVariablesDAO.ACTUAL + " INTEGER, "
                    + nivelesVariablesDAO.MINIMODESEADO + " INTEGER, "
                    + "FOREIGN KEY " + "("+nivelesVariablesDAO.IDEMPRESA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"),"
                    + "FOREIGN KEY " + "("+nivelesVariablesDAO.IDALMACEN+") REFERENCES " + almacenesDAO.TABLE_ALMACENES + "("+ almacenesDAO.IDALMACEN +"));";
   private static final String CREATE_TABLE_USUARIOS =
            "CREATE TABLE " + usuariosDAO.TABLE_UDUARIOS + " ("
                    + usuariosDAO.IDUSUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + usuariosDAO.NOBOLETA + " TEXT, "
                    + usuariosDAO.CONTRASENA + " TEXT, "
                    + usuariosDAO.NOMBRE + " TEXT, "
                    + usuariosDAO.APEPATERNO + " TEXT, "
                    + usuariosDAO.APEMATERNO + " TEXT, "
                    + usuariosDAO.GRUPO + " TEXT );";
    private static final String CREATE_TABLE_SESION =
            "CREATE TABLE " + sesionDAO.TABLE_SESION + " ("
                    + sesionDAO.IDSESION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + sesionDAO.IDUSUARIO + " INTEGER, "
                    + sesionDAO.ACTIVA + " INTEGER, "
                    + "FOREIGN KEY " + "("+sesionDAO.IDUSUARIO+") REFERENCES " + usuariosDAO.TABLE_UDUARIOS + "("+ usuariosDAO.IDUSUARIO +"));";
    private static final String CREATE_TABLE_CONFIGURACION =
            "CREATE TABLE " + configuracionDAO.TABLE_CONFIGURACION + " ("
                    + configuracionDAO.IDUSUARIO + " INTEGER, "
                    + configuracionDAO.IDTIPOCONFIGURACION + " INTEGER, "
                    + configuracionDAO.VALOR + " INTEGER, "
                    + configuracionDAO.FECMODIFICACION + " TEXT, "
                    + "FOREIGN KEY " + "("+configuracionDAO.IDUSUARIO+") REFERENCES " + usuariosDAO.TABLE_UDUARIOS + "("+ usuariosDAO.IDUSUARIO +"),"
                    + "FOREIGN KEY " + "("+configuracionDAO.IDTIPOCONFIGURACION+") REFERENCES " + tipoConfiguracionDAO.TABLE_TIPOCONFIGURACION + "("+ tipoConfiguracionDAO.IDTIPOCONFIGURACION +"));";
    private static final String CREATE_TABLE_TIPOCONFIGURACION =
            "CREATE TABLE " + tipoConfiguracionDAO.TABLE_TIPOCONFIGURACION + " ("
                    + tipoConfiguracionDAO.IDTIPOCONFIGURACION  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + tipoConfiguracionDAO.NBTIPOCONFIGURACION + " TEXT);";
    private static final String CREATE_TABLE_USUARIOSEMPRESAS =
            "CREATE TABLE " + usuariosEmpresasDAO.TABLE_UDUARIOSEMPRESAS + " ("
                    + usuariosEmpresasDAO.IDUSUARIO + " INTEGER, "
                    + usuariosEmpresasDAO.IDEMPRESA + " TEXT, "
                    + "FOREIGN KEY " + "("+usuariosEmpresasDAO.IDUSUARIO+") REFERENCES " + usuariosDAO.TABLE_UDUARIOS + "("+ usuariosDAO.IDUSUARIO +"),"
                    + "FOREIGN KEY " + "("+usuariosEmpresasDAO.IDEMPRESA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"));";
    private static final String CREATE_TABLE_REGLASCOMPENSACION =
            "CREATE TABLE " + reglascompensacionDAO.TABLEREGLASCOMPENSACION + " ("
            + reglascompensacionDAO.IDREGLA + " INTEGER, "
            + reglascompensacionDAO.NBREGLA + " TEXT );";
    private static final String CREATE_TABLE_EMPRESASPENALIZADAS =
            "CREATE TABLE " + empresasPenalizadasDAO.TABLEEMPRESASPENALIZADAS + " ("
            + empresasPenalizadasDAO.IDEMPRESA + " INTEGER, "
            + empresasPenalizadasDAO.IDREGLA + " INTEGER, "
            + empresasPenalizadasDAO.IDEMPRESAVICTIMA + " INTEGER, "
                    + "FOREIGN KEY " + "("+empresasPenalizadasDAO.IDEMPRESA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"),"
                    + "FOREIGN KEY " + "("+empresasPenalizadasDAO.IDREGLA+") REFERENCES " + reglascompensacionDAO.TABLEREGLASCOMPENSACION + "("+ reglascompensacionDAO.IDREGLA +"),"
                    + "FOREIGN KEY " + "("+empresasPenalizadasDAO.IDEMPRESAVICTIMA+") REFERENCES " + empresasDAO.TABLE_EMPRESAS + "("+ empresasDAO.IDEMPRESA +"));";
    private static final String CREATE_TABLE_ULTIMASINCRONIZACION=
            "CREATE TABLE " + ultimasincronizacionDAO.TABLE_UDUARIOSEMPRESAS + " ("
                    + ultimasincronizacionDAO.ID + " INTEGER, "
                    + ultimasincronizacionDAO.FECHA_SINCRONIZACION + " INTEGER );";

    private DataBaseHelper(Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_EMPRESAS);
        db.execSQL(CREATE_TABLE_INDUSTRIAS);
        db.execSQL(CREATE_TABLE_ALAMCENES);
        db.execSQL(CREATE_TABLE_TIPOALMACEN);
        db.execSQL(CREATE_TABLE_ENCADENAMIENTOS);
        db.execSQL(CREATE_TABLE_BALANCES);
        db.execSQL(CREATE_TABLE_CUENTAS);
        db.execSQL(CREATE_TABLE_SOLICITUDES);
        db.execSQL(CREATE_TABLE_COTIZACIONES);
        db.execSQL(CREATE_TABLE_COMPRAS);
        db.execSQL(CREATE_TABLE_COMPRASOPERACIONES);
        db.execSQL(CREATE_TABLE_PAGOS);
        db.execSQL(CREATE_TABLE_EMBARQUES);
        db.execSQL(CREATE_TABLE_TIPOSOPERACIONES);
        db.execSQL(CREATE_TABLE_NIVELESVARIABLES);
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_SESION);
        db.execSQL(CREATE_TABLE_CONFIGURACION);
        db.execSQL(CREATE_TABLE_TIPOCONFIGURACION);
        db.execSQL(CREATE_TABLE_USUARIOSEMPRESAS);
        db.execSQL(CREATE_TABLE_REGLASCOMPENSACION);
        db.execSQL(CREATE_TABLE_EMPRESASPENALIZADAS);
        db.execSQL(CREATE_TABLE_ULTIMASINCRONIZACION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
