package com.dragonregnan.sistemasdinamicos.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
import com.dragonregnan.sistemasdinamicos.dao.tipoConfiguracionDAO;
import com.dragonregnan.sistemasdinamicos.dao.tiposOperacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.ultimasincronizacionDAO;
import com.dragonregnan.sistemasdinamicos.dao.usuariosDAO;
import com.dragonregnan.sistemasdinamicos.dao.usuariosEmpresasDAO;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
import java.util.HashMap;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LoginActivity extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    private static String url = "http://ultragalaxia.com/android/usuario_empresa.php";
    private static String url2 = "http://ultragalaxia.com/android/nuevaEmpresa.php";
    private static String url3 = "http://ultragalaxia.com/android/sincronizacion.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EMPRESA = "empresa";
    private static final String TAG_USUARIO = "usuario";{}
    private static final String TAG_IDUSUARIO = "idusuario";
    private static final String TAG_NOBOLETA= "noboleta";
    private static final String TAG_CONTRASENA = "contrasena";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_APEPATERNO = "apepaterno";
    private static final String TAG_APEMATERNO = "apematerno";
    private static final String TAG_GRUPO = "grupo";
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mBoletaView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
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




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INSTANCIAR DAOS

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

        int id_Logueado = sesDAO.getActiva();

        Log.d("logeado", String.valueOf(id_Logueado));

        if(id_Logueado == 0){

            mBoletaView = (AutoCompleteTextView) findViewById(R.id.boleta);

            mPasswordView = (EditText) findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
        }else{
            int empresa = usuEmpDAO.getEmpresa(id_Logueado);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("idEmpresa", empresa);
            editor.putInt("idUsuario", id_Logueado);
            editor.commit();
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public static boolean verificaConexion(Context ctx) {
        Log.d("verificaConexion", "verificaConexion");
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

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // VACIAR ERRORES
        mBoletaView.setError(null);
        mPasswordView.setError(null);
        // GUARDAR VALORES AL MISMO TIEMPO QUE SE TRATA DE LOGUEAR
        String boleta = mBoletaView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // CHECAR VALIDEZ DE DATOS DE ACCESO
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(boleta)) {
            mBoletaView.setError(getString(R.string.error_field_required));
            focusView = mBoletaView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //MUESTRA EL SPINNER DEL PROGRESO
            showProgress(true);
            mAuthTask = new UserLoginTask(boleta, password);
            mAuthTask.execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mBoleta;
        private final String mPassword;
        private int id_usuario;

        UserLoginTask(String boleta, String password) {
            mBoleta = boleta;
            mPassword = password;
            id_usuario = 0;
        }

        private int isPasswordValid(String password, String boleta) {
            String comparacion = usuDAO.getContraseña(boleta);
            Log.d("contraseña:", comparacion);
            Log.d("contraseña pass:", password);
            if (comparacion.equals("vacio")) {
                return 0;
            }
            if (comparacion.equals(password)) {
                return 2;
            } else {
                return 1;
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            boolean ret = false;

            int comprobacion = isPasswordValid(mPassword, mBoleta);
            Log.d("comprobacion pass:", String.valueOf(comprobacion));
            if (comprobacion == 0) {

                if (!verificaConexion(LoginActivity.this)) {
                    Toast.makeText(getBaseContext(),
                            "Comprueba tu conexión a Internet", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("boleta", mBoleta));
                    params1.add(new BasicNameValuePair("password", mPassword));

                    JSONObject json = jsonParser.makeHttpRequest(url, "POST", params1);
                    Log.d("Create Response", json.toString());

                    try {
                        int success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {

                            int empresa = json.getInt(TAG_EMPRESA);

                            JSONArray usuario;
                            usuario = json.getJSONArray(TAG_USUARIO);
                            usuariosModel usuarios = new usuariosModel();
                            for (int i = 0; i < usuario.length(); i++) {
                                JSONObject c = usuario.getJSONObject(i);
                                id_usuario=c.getInt(TAG_IDUSUARIO);
                                usuarios.setIdUsuario(c.getInt(TAG_IDUSUARIO));
                                usuarios.setNoBoleta(c.getString(TAG_NOBOLETA));
                                usuarios.setContrasena(c.getString(TAG_CONTRASENA));
                                usuarios.setNombre(c.getString(TAG_NOMBRE));
                                usuarios.setApePaterno(c.getString(TAG_APEPATERNO));
                                usuarios.setApeMaterno(c.getString(TAG_APEMATERNO));
                                usuarios.setGrupo(c.getString(TAG_GRUPO));
                                usuDAO.insertUsuario(usuarios);
                            }
                            if (empresa == 0) {
                                Log.d("empresa ", String.valueOf(empresa));

                                List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                                params2.add(new BasicNameValuePair("idUsuario", String.valueOf(id_usuario)));
                                params2.add(new BasicNameValuePair("empresa", "Corporacion "+usuDAO.getNombre(mBoleta)));

                                jsonParser.makeHttpRequest(url2, "POST", params2);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ultimaDAO.getSincronizacion()== null){
                        List<NameValuePair> vacio = new ArrayList<NameValuePair>();

                        JSONObject json3 = jsonParser.makeHttpRequest(url3, "POST", vacio);
                        Log.d("Create Response", json3.toString());

                        try {
                            int success = json3.getInt(TAG_SUCCESS);
                            if (success == 1) {

                                JSONArray almacenes;
                                almacenes = json3.getJSONArray(almaDAO.TABLE_ALMACENES);

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
                                balances = json3.getJSONArray(balDAO.TABLE_BALANCES);

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
                                compras = json3.getJSONArray(comDAO.TABLE_COMPRAS);

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
                                comprasOperaciones = json3.getJSONArray(comOpeDAO.TABLE_COMPRASOPERACIONES);

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
                                cotizaciones = json3.getJSONArray(cotDAO.TABLE_COTIZACIONES);

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
                                cuentas = json3.getJSONArray(cueDAO.TABLE_CUENTAS);

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
                                embarques = json3.getJSONArray(embDAO.TABLE_EMBARQUES);

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
                                empresas = json3.getJSONArray(empDAO.TABLE_EMPRESAS);

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
                                encadenamientos = json3.getJSONArray(encDAO.TABLE_ENCADENAMIENTOS);

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
                                industrias = json3.getJSONArray(indDAO.TABLE_INDUSTRIAS);

                                industriasModel industria = new industriasModel();
                                // looping through All Products
                                for (int i = 0; i < industrias.length(); i++) {
                                    JSONObject c = industrias.getJSONObject(i);
                                    industria.setIdIndustria(c.getInt(indDAO.IDINDUSTRIA));
                                    industria.setNbIndustria(c.getString(indDAO.NBINDUSTRIA));
                                    indDAO.insertIndustria(industria);
                                }

                                JSONArray nivelesVariables;
                                nivelesVariables = json3.getJSONArray(nivVarDAO.TABLE_NIVELESVARIABLES);

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
                                pagos = json3.getJSONArray(pagDAO.TABLE_PAGOS);

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
                                solicitudes = json3.getJSONArray(solDAO.TABLE_SOLICITUDES);

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
                                tipoAlmacenes = json3.getJSONArray("tipoalmacenes");

                                tipoAlmacenModel tipoAlmacen = new tipoAlmacenModel();
                                // looping through All Products
                                for (int i = 0; i < tipoAlmacenes.length(); i++) {
                                    JSONObject c = tipoAlmacenes.getJSONObject(i);
                                    tipoAlmacen.setIdTipoAlmacen(c.getInt(tipAlmDAO.IDTIPOALMACEN));
                                    tipoAlmacen.setNbTipoAlmacen(c.getString(tipAlmDAO.NBTIPOALMACEN));
                                    tipAlmDAO.insertTipoAlmacen(tipoAlmacen);
                                }

                                JSONArray tipoOperaciones;
                                tipoOperaciones = json3.getJSONArray(TipOpeDAO.TABLE_TIPOOPERACIONES);

                                tiposOperacionesModel tipoOperacion = new tiposOperacionesModel();
                                // looping through All Products
                                for (int i = 0; i < tipoOperaciones.length(); i++) {
                                    JSONObject c = tipoOperaciones.getJSONObject(i);
                                    tipoOperacion.setIdTipoOperacion(c.getInt(TipOpeDAO.IDTIPOOPERACION));
                                    tipoOperacion.setNbTipoOperacion(c.getString(TipOpeDAO.NBTIPOOPERACION));
                                    TipOpeDAO.insertTipoAlmacen(tipoOperacion);
                                }
                                JSONArray usuariosEmpresas;
                                usuariosEmpresas = json3.getJSONArray(usuEmpDAO.TABLE_UDUARIOSEMPRESAS);

                                usuariosEmpresasModel usuarioEmpresa = new usuariosEmpresasModel();
                                // looping through All Products
                                for (int i = 0; i < usuariosEmpresas.length(); i++) {
                                    JSONObject c = usuariosEmpresas.getJSONObject(i);
                                    usuarioEmpresa.setIdUsuario(c.getInt(usuEmpDAO.IDUSUARIO));
                                    usuarioEmpresa.setIdEmpresa(c.getInt(usuEmpDAO.IDEMPRESA));
                                    usuEmpDAO.insertUsuariosEmpresas(usuarioEmpresa);
                                }

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar cal = Calendar.getInstance();
                                ultimaDAO.registrarSincronizacion(dateFormat.format(cal.getTime()));
                                ret= true;
                                Log.d("sincronizar", "sincronizar");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (comprobacion == 1) {
                ret = false;
            } else if (comprobacion == 2) {
                Log.d("comprobacion", "comprobacion");

                if (ultimaDAO.getSincronizacion()== null){
                    Log.d("getSincronizacion", ultimaDAO.getSincronizacion());
                    List<NameValuePair> vacio = new ArrayList<NameValuePair>();

                    JSONObject json3 = jsonParser.makeHttpRequest(url3, "POST", vacio);
                    Log.d("Create Response", json3.toString());

                    try {
                        int success = json3.getInt(TAG_SUCCESS);
                        if (success == 1) {

                            JSONArray almacenes;
                            almacenes = json3.getJSONArray(almaDAO.TABLE_ALMACENES);

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
                            balances = json3.getJSONArray(balDAO.TABLE_BALANCES);

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
                            compras = json3.getJSONArray(comDAO.TABLE_COMPRAS);

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
                            comprasOperaciones = json3.getJSONArray(comOpeDAO.TABLE_COMPRASOPERACIONES);

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
                            cotizaciones = json3.getJSONArray(cotDAO.TABLE_COTIZACIONES);

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
                            cuentas = json3.getJSONArray(cueDAO.TABLE_CUENTAS);

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
                            embarques = json3.getJSONArray(embDAO.TABLE_EMBARQUES);

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
                            empresas = json3.getJSONArray(empDAO.TABLE_EMPRESAS);

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
                            encadenamientos = json3.getJSONArray(encDAO.TABLE_ENCADENAMIENTOS);

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
                            industrias = json3.getJSONArray(indDAO.TABLE_INDUSTRIAS);

                            industriasModel industria = new industriasModel();
                            // looping through All Products
                            for (int i = 0; i < industrias.length(); i++) {
                                JSONObject c = industrias.getJSONObject(i);
                                industria.setIdIndustria(c.getInt(indDAO.IDINDUSTRIA));
                                industria.setNbIndustria(c.getString(indDAO.NBINDUSTRIA));
                                indDAO.insertIndustria(industria);
                            }

                            JSONArray nivelesVariables;
                            nivelesVariables = json3.getJSONArray(nivVarDAO.TABLE_NIVELESVARIABLES);

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
                            pagos = json3.getJSONArray(pagDAO.TABLE_PAGOS);

                            pagosModel pago = new pagosModel();
                            // looping through All Products
                            for (int i = 0; i < pagos.length(); i++) {
                                JSONObject c = pagos.getJSONObject(i);
                                pago.setIdPago(c.getInt(pagDAO.IDPAGO));
                                pago.setIdOperacion(c.getInt(pagDAO.IDOPERACION));
                                pago.setCantidadPagada(c.getInt(pagDAO.CANTIDADPAGADA));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                pago.setFecPago(c.getString(pagDAO.FECPAGO));
                                pagDAO.insertPago(pago);
                            }
                            JSONArray solicitudes;
                            solicitudes = json3.getJSONArray(solDAO.TABLE_SOLICITUDES);

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
                            tipoAlmacenes = json3.getJSONArray("tipoalmacenes");

                            tipoAlmacenModel tipoAlmacen = new tipoAlmacenModel();
                            // looping through All Products
                            for (int i = 0; i < tipoAlmacenes.length(); i++) {
                                JSONObject c = tipoAlmacenes.getJSONObject(i);
                                tipoAlmacen.setIdTipoAlmacen(c.getInt(tipAlmDAO.IDTIPOALMACEN));
                                tipoAlmacen.setNbTipoAlmacen(c.getString(tipAlmDAO.NBTIPOALMACEN));
                                tipAlmDAO.insertTipoAlmacen(tipoAlmacen);
                            }

                            JSONArray tipoOperaciones;
                            tipoOperaciones = json3.getJSONArray(TipOpeDAO.TABLE_TIPOOPERACIONES);

                            tiposOperacionesModel tipoOperacion = new tiposOperacionesModel();
                            // looping through All Products
                            for (int i = 0; i < tipoOperaciones.length(); i++) {
                                JSONObject c = tipoOperaciones.getJSONObject(i);
                                tipoOperacion.setIdTipoOperacion(c.getInt(TipOpeDAO.IDTIPOOPERACION));
                                tipoOperacion.setNbTipoOperacion(c.getString(TipOpeDAO.NBTIPOOPERACION));
                                TipOpeDAO.insertTipoAlmacen(tipoOperacion);
                            }
                            JSONArray usuariosEmpresas;
                            usuariosEmpresas = json3.getJSONArray(usuEmpDAO.TABLE_UDUARIOSEMPRESAS);

                            usuariosEmpresasModel usuarioEmpresa = new usuariosEmpresasModel();
                            // looping through All Products
                            for (int i = 0; i < usuariosEmpresas.length(); i++) {
                                JSONObject c = usuariosEmpresas.getJSONObject(i);
                                usuarioEmpresa.setIdUsuario(c.getInt(usuEmpDAO.IDUSUARIO));
                                usuarioEmpresa.setIdEmpresa(c.getInt(usuEmpDAO.IDEMPRESA));
                                usuEmpDAO.insertUsuariosEmpresas(usuarioEmpresa);
                            }

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar cal = Calendar.getInstance();
                            ultimaDAO.registrarSincronizacion(dateFormat.format(cal.getTime()));
                            ret= true;
                            Log.d("sincronizar", "sincronizar");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return ret;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            Log.d("onPostExecute", "onPostExecute");
            if (success) {
                //SE CREAN LAS PREFERENCIAS
                Log.d("success", "success");
                if(id_usuario == 0){
                    id_usuario = usuDAO.getIdusuario(mBoleta);
                }
                int empresa = usuEmpDAO.getEmpresa(id_usuario);
                Log.d("id_usuario", String.valueOf(id_usuario));
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("idEmpresa", empresa);
                editor.putInt("idUsuario", id_usuario);
                editor.commit();
                sesionModel sesion = new sesionModel();
                sesion.setIdUsuario(id_usuario);
                sesion.setActiva(1);
                sesDAO.insertSesion(sesion);
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

