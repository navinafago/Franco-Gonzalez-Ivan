package com.dragonregnan.sistemasdinamicos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.JSON.JSONParser;
import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.almacenesDAO;
import com.dragonregnan.sistemasdinamicos.dao.balancesDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasOperacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.cuentasDAO;
import com.dragonregnan.sistemasdinamicos.dao.embarquesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.encadenamientosDAO;
import com.dragonregnan.sistemasdinamicos.dao.industriasDAO;
import com.dragonregnan.sistemasdinamicos.dao.nivelesVariablesDAO;
import com.dragonregnan.sistemasdinamicos.dao.pagosDAO;
import com.dragonregnan.sistemasdinamicos.dao.sesionDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.dao.tipoAlmacenDAO;
import com.dragonregnan.sistemasdinamicos.dao.tiposOperacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.ultimasincronizacionDAO;
import com.dragonregnan.sistemasdinamicos.dao.usuariosDAO;
import com.dragonregnan.sistemasdinamicos.dao.usuariosEmpresasDAO;
import com.dragonregnan.sistemasdinamicos.fragments.FragmentHistorial;
import com.dragonregnan.sistemasdinamicos.fragments.FragmentMisCotizaciones;
import com.dragonregnan.sistemasdinamicos.fragments.FragmentMisSolicitudes;
import com.dragonregnan.sistemasdinamicos.fragments.FragmentSolicitudes;
import com.dragonregnan.sistemasdinamicos.model.almacenesModel;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.comprasOperacionesModel;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.cuentasModel;
import com.dragonregnan.sistemasdinamicos.model.embarquesModel;
import com.dragonregnan.sistemasdinamicos.model.empresasModel;
import com.dragonregnan.sistemasdinamicos.model.encadenamientosModel;
import com.dragonregnan.sistemasdinamicos.model.industriasModel;
import com.dragonregnan.sistemasdinamicos.model.nivelesVariablesModel;
import com.dragonregnan.sistemasdinamicos.model.pagosModel;
import com.dragonregnan.sistemasdinamicos.model.sesionModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;
import com.dragonregnan.sistemasdinamicos.model.tipoAlmacenModel;
import com.dragonregnan.sistemasdinamicos.model.tiposOperacionesModel;
import com.dragonregnan.sistemasdinamicos.model.usuariosEmpresasModel;
import com.dragonregnan.sistemasdinamicos.model.usuariosModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by laura on 27/01/2016.
 */
public class MercadoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTabHost mTabHost;
    JSONParser jsonParser = new JSONParser();
    private usuariosDAO usuDAO;
    private sesionDAO sesDAO;
    private usuariosEmpresasDAO usuEmpDAO;
    private ultimasincronizacionDAO ultimaDAO;
    private almacenesDAO almaDAO;
    private balancesDAO balDAO;
    private comprasDAO comDAO;
    private comprasOperacionesDAO comOpeDAO;
    private cotizacionesDAO cotDAO;
    private cuentasDAO cueDAO;
    private embarquesDAO embDAO;
    private empresasDAO empDAO;
    private encadenamientosDAO encDAO;
    private industriasDAO indDAO;
    private nivelesVariablesDAO nivVarDAO;
    private pagosDAO pagDAO;
    private solicitudesDAO solDAO;
    private tipoAlmacenDAO tipAlmDAO;
    private tiposOperacionesDAO TipOpeDAO;
    private static String url = "http://ultragalaxia.com/android/sincronizacionFecha.php";
    private static String url2 = "http://ultragalaxia.com/android/insertnivel.php";
    private static String url3 = "http://ultragalaxia.com/android/insertbalance.php";
    private static final String TAG_SUCCESS = "success";
    private int idEmpresa = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mercadoactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        idEmpresa = pref.getInt("idEmpresa", 0);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        usuDAO = new usuariosDAO(this);
        sesDAO = new sesionDAO(this);
        ultimaDAO =new ultimasincronizacionDAO(this);
        usuEmpDAO = new usuariosEmpresasDAO(this);
        almaDAO =  new almacenesDAO(this);
        balDAO = new balancesDAO(this);
        comDAO = new comprasDAO(this) ;
        comOpeDAO = new comprasOperacionesDAO(this);
        cotDAO = new cotizacionesDAO(this);
        cueDAO = new cuentasDAO(this);
        embDAO = new embarquesDAO(this);
        empDAO = new empresasDAO(this);
        encDAO = new encadenamientosDAO(this);
        indDAO = new industriasDAO(this);
        nivVarDAO = new nivelesVariablesDAO(this);
        pagDAO = new pagosDAO(this);
        solDAO = new solicitudesDAO(this);
        tipAlmDAO = new tipoAlmacenDAO(this);
        TipOpeDAO= new tiposOperacionesDAO(this);

        if (!verificaConexion(MercadoActivity.this)) {
            Toast.makeText(getBaseContext(),
                    "Comprueba tu conexión a Internet", Toast.LENGTH_SHORT)
                    .show();
        }
        else {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("fecha", ultimaDAO.getSincronizacion()));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params1);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray almacenes;
                    almacenes = json.getJSONArray(almaDAO.TABLE_ALMACENES);

                    almacenesModel almacen = new almacenesModel();
                    // looping through All Products
                    for (int i = 0; i < almacenes.length(); i++) {
                        JSONObject c = almacenes.getJSONObject(i);
                        almacen.setIdAlmacen(c.getInt(almaDAO.IDALMACEN));
                        almacen.setIdEmpresa(c.getInt(almaDAO.IDEMPRESA));
                        almacen.setIdTipoAlmacen(c.getInt(almaDAO.IDTIPOALMACEN));
                        almacen.setMaxAlmacen(c.getInt(almaDAO.MAXALMACEN));
                        almacen.setNbAlmacen(c.getString(almaDAO.NBALMACEN));
                        almaDAO.insertAlmacen(almacen);
                    }

                    JSONArray balances;
                    balances = json.getJSONArray(balDAO.TABLE_BALANCES);

                    balancesModel balance = new balancesModel();
                    // looping through All Products
                    for (int i = 0; i < balances.length(); i++) {
                        JSONObject c = balances.getJSONObject(i);
                        balance.setIdEmpresa(c.getInt(balDAO.IDEMPRESA));
                        balance.setIdCuenta(c.getInt(balDAO.IDCUENTA));
                        balance.setSaldo(c.getInt(balDAO.SALDO));
                        balance.setFecBalance(c.getString(balDAO.FECBALANCE));
                        balDAO.insertCuentaBalance(balance);
                    }

                    JSONArray compras;
                    compras = json.getJSONArray(comDAO.TABLE_COMPRAS);

                    comprasModel compra = new comprasModel();
                    // looping through All Products
                    for (int i = 0; i < compras.length(); i++) {
                        JSONObject c = compras.getJSONObject(i);
                        compra.setIdCotizacion(c.getInt(comDAO.IDCOTIZACION));
                        compra.setIdCompra(c.getInt(comDAO.IDCOMPRA));
                        compra.setFecCompra(c.getString(comDAO.FECCOMPRA));
                        Boolean entregada = true;
                        if(c.getInt(comDAO.ENTREGADA)== 1){
                            entregada = true;
                        }else{
                            entregada=false;
                        }
                        compra.setEntregada(entregada);
                        Boolean liquidada = true;
                        if(c.getInt(comDAO.LIQUIDADA)== 1){
                            liquidada = true;
                        }else{
                            liquidada=false;
                        }
                        compra.setLiquidada(liquidada);
                        comDAO.insertCompras(compra);
                    }
                    JSONArray comprasOperaciones;
                    comprasOperaciones = json.getJSONArray(comOpeDAO.TABLE_COMPRASOPERACIONES);

                    comprasOperacionesModel compraOperacion = new comprasOperacionesModel();
                    // looping through All Products
                    for (int i = 0; i < comprasOperaciones.length(); i++) {
                        JSONObject c = comprasOperaciones.getJSONObject(i);
                        compraOperacion.setIdCompra(c.getInt(comOpeDAO.IDCOMPRA));
                        compraOperacion.setIdOperacion(c.getInt(comOpeDAO.IDOPERACION));
                        compraOperacion.setIdEmpresaVendedora(c.getInt(comOpeDAO.IDEMPRESAVENDEDORA));
                        compraOperacion.setIdEmpresaCompradora(c.getInt(comOpeDAO.IDEMPRESACOMPRADORA));
                        compraOperacion.setIdTipoOperacion(c.getInt(comOpeDAO.IDTIPOOPERACION));
                        comOpeDAO.insertCompraOperacion(compraOperacion);
                    }
                    JSONArray cotizaciones;
                    cotizaciones = json.getJSONArray(cotDAO.TABLE_COTIZACIONES);

                    cotizacionesModel cotizacion = new cotizacionesModel();
                    // looping through All Products
                    for (int i = 0; i < cotizaciones.length(); i++) {
                        JSONObject c = cotizaciones.getJSONObject(i);
                        cotizacion.setIdCotizacion(c.getInt(cotDAO.IDCOTIZACION));
                        cotizacion.setIdSolicitud(c.getInt(cotDAO.IDSOLICITUD));
                        cotizacion.setIdEmpresaVendedora(c.getInt(cotDAO.IDEMPRESAVENDEDORA));
                        cotizacion.setCantOfrecida(c.getInt(cotDAO.CANTOFRECIDA));
                        cotizacion.setPrecio(c.getInt(cotDAO.PRECIO));
                        cotizacion.setEstado(c.getInt(cotDAO.ESTADO));
                        cotizacion.setFecExpiracion(c.getString(cotDAO.FECEXPIRACION));
                        cotizacion.setFecEntrega(c.getString(cotDAO.FECENTREGA));
                        cotDAO.insertCotizacion(cotizacion);
                    }
                    JSONArray cuentas;
                    cuentas = json.getJSONArray(cueDAO.TABLE_CUENTAS);

                    cuentasModel cuenta = new cuentasModel();
                    // looping through All Products
                    for (int i = 0; i < cuentas.length(); i++) {
                        JSONObject c = cuentas.getJSONObject(i);
                        cuenta.setIdCuenta(c.getInt(cueDAO.IDCUENTA));
                        cuenta.setNbCuenta(c.getString(cueDAO.NBCUENTA));
                        Boolean acreedora = true;
                        if(c.getInt(cueDAO.ACREEDORA)== 1){
                            acreedora = true;
                        }else{
                            acreedora=false;
                        }
                        cuenta.setAcreedora(acreedora);
                        cueDAO.insertCuenta(cuenta);
                    }
                    JSONArray embarques;
                    embarques = json.getJSONArray(embDAO.TABLE_EMBARQUES);

                    embarquesModel embarque = new embarquesModel();
                    // looping through All Products
                    for (int i = 0; i < embarques.length(); i++) {
                        JSONObject c = embarques.getJSONObject(i);
                        embarque.setIdEmbarque(c.getInt(embDAO.IDEMBARQUE));
                        embarque.setIdOperacion(c.getInt(embDAO.IDOPERACION));
                        embarque.setCantidadEmbarcada(c.getInt(embDAO.CANTIDADEMBARCADA));
                        embarque.setFecEmbarque(c.getString(embDAO.FECEMBARQUE));
                        embDAO.insertEmbarque(embarque);
                    }
                    JSONArray empresas;
                    empresas = json.getJSONArray(empDAO.TABLE_EMPRESAS);

                    empresasModel empresa = new empresasModel();
                    // looping through All Products
                    for (int i = 0; i < empresas.length(); i++) {
                        JSONObject c = empresas.getJSONObject(i);
                        empresa.setIdEmpresa(c.getInt(empDAO.IDEMPRESA));
                        empresa.setIdIndustria(c.getInt(empDAO.IDINDUSTRIA));
                        empresa.setNbEmpresa(c.getString(empDAO.NBEMPRESA));
                        empDAO.insertEmpresa(empresa);
                    }
                    JSONArray encadenamientos;
                    encadenamientos = json.getJSONArray(almaDAO.TABLE_ALMACENES);

                    encadenamientosModel encadenamiento = new encadenamientosModel();
                    // looping through All Products
                    for (int i = 0; i < encadenamientos.length(); i++) {
                        JSONObject c = encadenamientos.getJSONObject(i);
                        encadenamiento.setIdIndustriaCompradora(c.getInt(encDAO.IDINDUSTRIACOMPRADORA));
                        encadenamiento.setIdIndustriaVendedora(c.getInt(encDAO.IDINDUSTRIAVENDEDORA));
                        encadenamiento.setCoeficiente(c.getInt(encDAO.COEFICIENTE));
                        encDAO.insertEncadenamiento(encadenamiento);
                    }
                    JSONArray industrias;
                    industrias = json.getJSONArray(indDAO.TABLE_INDUSTRIAS);

                    industriasModel industria = new industriasModel();
                    // looping through All Products
                    for (int i = 0; i < industrias.length(); i++) {
                        JSONObject c = industrias.getJSONObject(i);
                        industria.setIdIndustria(c.getInt(indDAO.IDINDUSTRIA));
                        industria.setNbIndustria(c.getString(indDAO.NBINDUSTRIA));
                        indDAO.insertIndustria(industria);
                    }

                    JSONArray nivelesVariables;
                    nivelesVariables = json.getJSONArray(nivVarDAO.TABLE_NIVELESVARIABLES);

                    nivelesVariablesModel niveleVariable = new nivelesVariablesModel();
                    // looping through All Products
                    for (int i = 0; i < nivelesVariables.length(); i++) {
                        JSONObject c = nivelesVariables.getJSONObject(i);
                        niveleVariable.setIdAlmacen(c.getInt(nivVarDAO.IDALMACEN));
                        niveleVariable.setIdEmpresa(c.getInt(nivVarDAO.IDEMPRESA));
                        niveleVariable.setDeseado(c.getInt(nivVarDAO.DESEADO));
                        niveleVariable.setActual(c.getInt(nivVarDAO.ACTUAL));
                        niveleVariable.setMinimoDeseado(c.getInt(nivVarDAO.MINIMODESEADO));
                        nivVarDAO.insertNivel(niveleVariable);
                    }
                    JSONArray pagos;
                    pagos = json.getJSONArray(pagDAO.TABLE_PAGOS);

                    pagosModel pago = new pagosModel();
                    // looping through All Products
                    for (int i = 0; i < pagos.length(); i++) {
                        JSONObject c = pagos.getJSONObject(i);
                        pago.setIdPago(c.getInt(pagDAO.IDPAGO));
                        pago.setIdOperacion(c.getInt(pagDAO.IDOPERACION));
                        pago.setCantidadPagada(c.getInt(pagDAO.CANTIDADPAGADA));
                        pago.setFecPago(c.getString(pagDAO.FECPAGO));
                        pagDAO.insertPago(pago);
                    }
                    JSONArray solicitudes;
                    solicitudes = json.getJSONArray(solDAO.TABLE_SOLICITUDES);

                    solicitudesModel solicitud = new solicitudesModel();
                    // looping through All Products
                    for (int i = 0; i < solicitudes.length(); i++) {
                        JSONObject c = solicitudes.getJSONObject(i);
                        solicitud.setIdSolicitud(c.getInt(solDAO.IDSOLICITUD));
                        solicitud.setIdIndustria(c.getInt(solDAO.IDINDUSTRIA));
                        solicitud.setIdEmpresaCompradora(c.getInt(solDAO.IDEMPRESACOMPRADORA));
                        solicitud.setCantSolicitada(c.getInt(solDAO.CANTSOLICITADA));
                        solicitud.setFecEntregaSol(c.getString(solDAO.FECENTREGASOL));
                        solDAO.insertSolicitud(solicitud);
                    }
                    JSONArray tipoAlmacenes;
                    tipoAlmacenes = json.getJSONArray("tipoalmacenes");

                    tipoAlmacenModel tipoAlmacen = new tipoAlmacenModel();
                    // looping through All Products
                    for (int i = 0; i < tipoAlmacenes.length(); i++) {
                        JSONObject c = tipoAlmacenes.getJSONObject(i);
                        tipoAlmacen.setIdTipoAlmacen(c.getInt(tipAlmDAO.IDTIPOALMACEN));
                        tipoAlmacen.setNbTipoAlmacen(c.getString(tipAlmDAO.NBTIPOALMACEN));
                        tipAlmDAO.insertTipoAlmacen(tipoAlmacen);
                    }

                    JSONArray tipoOperaciones;
                    tipoOperaciones = json.getJSONArray(TipOpeDAO.TABLE_TIPOOPERACIONES);

                    tiposOperacionesModel tipoOperacion = new tiposOperacionesModel();
                    // looping through All Products
                    for (int i = 0; i < tipoOperaciones.length(); i++) {
                        JSONObject c = tipoOperaciones.getJSONObject(i);
                        tipoOperacion.setIdTipoOperacion(c.getInt(TipOpeDAO.IDTIPOOPERACION));
                        tipoOperacion.setNbTipoOperacion(c.getString(TipOpeDAO.NBTIPOOPERACION));
                        TipOpeDAO.insertTipoAlmacen(tipoOperacion);
                    }
                    JSONArray usuariosEmpresas;
                    usuariosEmpresas = json.getJSONArray(usuEmpDAO.TABLE_UDUARIOSEMPRESAS);

                    usuariosEmpresasModel usuarioEmpresa = new usuariosEmpresasModel();
                    // looping through All Products
                    for (int i = 0; i < usuariosEmpresas.length(); i++) {
                        JSONObject c = usuariosEmpresas.getJSONObject(i);
                        usuarioEmpresa.setIdUsuario(c.getInt(usuEmpDAO.IDUSUARIO));
                        usuarioEmpresa.setIdEmpresa(c.getInt(usuEmpDAO.IDEMPRESA));
                        usuEmpDAO.insertUsuariosEmpresas(usuarioEmpresa);
                    }
                    Calendar cal = Calendar.getInstance();
                    Date registro = null;
                    try {
                        registro = new Date( format.parse(String.valueOf(cal.getTime())).getDate());
                        ultimaDAO.registrarSincronizacion(registro.toString());
                    } catch (android.net.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int almacen1 = almaDAO.getIdAlmacen(idEmpresa,1);
            int almacen2 = almaDAO.getIdAlmacen(idEmpresa,2);
            int produccion = almaDAO.getIdAlmacen(idEmpresa,3);
            int mercancias = almaDAO.getIdAlmacen(idEmpresa,4);

            List<NameValuePair> niveles1 = new ArrayList<NameValuePair>();
            niveles1.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            niveles1.add(new BasicNameValuePair("idcuenta",String.valueOf(almacen1)));
            niveles1.add(new BasicNameValuePair("actual",String.valueOf(nivVarDAO.getActual(idEmpresa, almacen1))));
            niveles1.add(new BasicNameValuePair("deseado",String.valueOf(nivVarDAO.getDeseaso(idEmpresa, almacen1))));
            niveles1.add(new BasicNameValuePair("minimodeseado",String.valueOf(nivVarDAO.getMinimoDeseado(idEmpresa, almacen1))));
            JSONObject json1 = jsonParser.makeHttpRequest(url2, "POST", niveles1);

            try {
                int success = json1.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> niveles2 = new ArrayList<NameValuePair>();
            niveles2.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            niveles2.add(new BasicNameValuePair("idcuenta",String.valueOf(almacen2)));
            niveles2.add(new BasicNameValuePair("actual",String.valueOf(nivVarDAO.getActual(idEmpresa, almacen2))));
            niveles2.add(new BasicNameValuePair("deseado",String.valueOf(nivVarDAO.getDeseaso(idEmpresa, almacen2))));
            niveles2.add(new BasicNameValuePair("minimodeseado",String.valueOf(nivVarDAO.getMinimoDeseado(idEmpresa, almacen2))));
            JSONObject json2 = jsonParser.makeHttpRequest(url2, "POST", niveles2);

            try {
                int success = json2.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> niveles3 = new ArrayList<NameValuePair>();
            niveles3.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            niveles3.add(new BasicNameValuePair("idcuenta",String.valueOf(produccion)));
            niveles3.add(new BasicNameValuePair("actual",String.valueOf(nivVarDAO.getActual(idEmpresa, produccion))));
            niveles3.add(new BasicNameValuePair("deseado",String.valueOf(nivVarDAO.getDeseaso(idEmpresa, produccion))));
            niveles3.add(new BasicNameValuePair("minimodeseado",String.valueOf(nivVarDAO.getMinimoDeseado(idEmpresa, produccion))));
            JSONObject json3 = jsonParser.makeHttpRequest(url2, "POST", niveles3);

            try {
                int success = json3.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> niveles4 = new ArrayList<NameValuePair>();
            niveles4.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            niveles4.add(new BasicNameValuePair("idcuenta",String.valueOf(mercancias)));
            niveles4.add(new BasicNameValuePair("actual",String.valueOf(nivVarDAO.getActual(idEmpresa, mercancias))));
            niveles4.add(new BasicNameValuePair("deseado",String.valueOf(nivVarDAO.getDeseaso(idEmpresa, mercancias))));
            niveles4.add(new BasicNameValuePair("minimodeseado",String.valueOf(nivVarDAO.getMinimoDeseado(idEmpresa, mercancias))));
            JSONObject json4 = jsonParser.makeHttpRequest(url2, "POST", niveles4);

            try {
                int success = json4.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }

            List<NameValuePair> balance1 = new ArrayList<NameValuePair>();
            balance1.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance1.add(new BasicNameValuePair("idcuenta",String.valueOf(1)));
            balance1.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa, 1))));
            JSONObject json5 = jsonParser.makeHttpRequest(url3, "POST", balance1);

            try {
                int success = json5.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }

            List<NameValuePair> balance2 = new ArrayList<NameValuePair>();
            balance2.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance2.add(new BasicNameValuePair("idcuenta",String.valueOf(2)));
            balance2.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,2))));
            JSONObject json6 = jsonParser.makeHttpRequest(url3, "POST", balance2);

            try {
                int success = json6.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }

            List<NameValuePair> balance3 = new ArrayList<NameValuePair>();
            balance3.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance3.add(new BasicNameValuePair("idcuenta",String.valueOf(3)));
            balance3.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,3))));
            JSONObject json7 = jsonParser.makeHttpRequest(url3, "POST", balance3);

            try {
                int success = json7.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance4 = new ArrayList<NameValuePair>();
            balance4.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance4.add(new BasicNameValuePair("idcuenta",String.valueOf(4)));
            balance4.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,4))));
            JSONObject json8 = jsonParser.makeHttpRequest(url3, "POST", balance4);

            try {
                int success = json8.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance5 = new ArrayList<NameValuePair>();
            balance5.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance5.add(new BasicNameValuePair("idcuenta",String.valueOf(5)));
            balance5.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,5))));
            JSONObject json9 = jsonParser.makeHttpRequest(url3, "POST", balance5);

            try {
                int success = json9.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance6 = new ArrayList<NameValuePair>();
            balance6.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance6.add(new BasicNameValuePair("idcuenta",String.valueOf(6)));
            balance6.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,6))));
            JSONObject json10 = jsonParser.makeHttpRequest(url3, "POST", balance6);

            try {
                int success = json10.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance7 = new ArrayList<NameValuePair>();
            balance7.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance7.add(new BasicNameValuePair("idcuenta",String.valueOf(7)));
            balance7.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,7))));
            JSONObject json11 = jsonParser.makeHttpRequest(url3, "POST", balance7);

            try {
                int success = json11.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance8 = new ArrayList<NameValuePair>();
            balance8.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance8.add(new BasicNameValuePair("idcuenta",String.valueOf(8)));
            balance8.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,8))));
            JSONObject json12 = jsonParser.makeHttpRequest(url3, "POST", balance8);

            try {
                int success = json12.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
            List<NameValuePair> balance9 = new ArrayList<NameValuePair>();
            balance9.add(new BasicNameValuePair("idempresa",String.valueOf(idEmpresa)));
            balance9.add(new BasicNameValuePair("idcuenta",String.valueOf(9)));
            balance9.add(new BasicNameValuePair("saldo",String.valueOf(balDAO.getSaldo(idEmpresa,9))));
            JSONObject json13 = jsonParser.makeHttpRequest(url3, "POST", balance9);

            try {
                int success = json13.getInt(TAG_SUCCESS);} catch (JSONException e) {
                e.printStackTrace();
            }
        }

    // INSTANCIAR EL LAYOUT DE TABS PARA LOS FRAGMENTS

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

    // ASIGNAR UN NOMBRE A CADA TAB E INSERTAR SU FRAGMENT

        mTabHost.addTab(
                mTabHost.newTabSpec("solicitud").setIndicator(" Solicitudes", null),
                FragmentSolicitudes.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("misolicitud").setIndicator("Mis Solicitudes", null),
                FragmentMisSolicitudes.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("micotizacion").setIndicator("Mis Cotizaciones", null),
                FragmentMisCotizaciones.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("hitorial").setIndicator("Historial", null),
                FragmentHistorial.class, null);

        }
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_mercado) {

        } else if (id == R.id.nav_balance) {
            Intent i = new Intent(this,BalanceActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
