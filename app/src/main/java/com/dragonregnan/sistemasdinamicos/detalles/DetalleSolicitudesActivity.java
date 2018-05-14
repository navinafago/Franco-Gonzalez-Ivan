package com.dragonregnan.sistemasdinamicos.detalles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.JSON.JSONParser;
import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasPenalizadasDAO;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laura on 16/02/2016.
 */
public class DetalleSolicitudesActivity extends ActionBarActivity {

    private int idSolicitud;
    private int idEmpresaCompradora;
    private int CantidadSolicitada;
    private String fechaSolicitada;
    private empresasDAO empresaDAO;
    private empresasPenalizadasDAO empPena;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://ultragalaxia.com/android/insertcotizacion.php";
    private static final String TAG_SUCCESS = "success";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_solicitudes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    //OBTENER EXTRAS DEL ACTIVITY PADRE
        Bundle extras = getIntent().getExtras();
        idSolicitud = extras.getInt("idSol");
        idEmpresaCompradora = extras.getInt("idEmpCompradora");
        CantidadSolicitada = extras.getInt("Cantidad");
        fechaSolicitada = extras.getString("Fecha");

        empresaDAO = new empresasDAO(this);
        empPena = new empresasPenalizadasDAO(this);

    //MOSTRAR DATOS DE LA SOLICITUD

        TextView empresaSol = (TextView) findViewById(R.id.txtvw_empresasol);
        TextView cantidadSolicitada = (TextView) findViewById(R.id.txtvw_cantidad);
        TextView fecha = (TextView) findViewById(R.id.txtvw_fecha);
        TextView penalizaciones = (TextView) findViewById(R.id.txtvw_empresasolrep);
        final EditText cantOfre = (EditText) findViewById(R.id.edtxt_cantidadofr);
        final EditText precOfre = (EditText) findViewById(R.id.edtxt_precioofre);
        final EditText total = (EditText) findViewById(R.id.edtxt_total);
        final EditText fechaOfre = (EditText) findViewById(R.id.edtxt_fechatent);
        final EditText fechaCaduca = (EditText) findViewById(R.id.edtxt_fechavencimiento);

        empresaSol.setText(empresaDAO.getNombreEmpresa(idEmpresaCompradora));
        cantidadSolicitada.setText("Cantidad Solicitada: "+ String.valueOf(CantidadSolicitada));
        fecha.setText("Fecha de Entrega: "+ fechaSolicitada);
        penalizaciones.setText("Penalizaciones de la empresa Vendedora: "+ empPena.getPenalizaciones(idEmpresaCompradora));
        total.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                total.setText(Integer.valueOf(String.valueOf(cantOfre.getText())) * Integer.valueOf(String.valueOf(precOfre.getText())));

            }
        });

    //ENVIAR DATOS DE LA NUEVA COTIZACION

        Button bt = (Button) findViewById(R.id.enviar_cotizacion);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                int idEmpresa = pref.getInt("idEmpresa", 0);

                cotizacionesModel cotizacion = new cotizacionesModel();
                cotizacion.setIdSolicitud(idSolicitud);
                cotizacion.setCantOfrecida(Integer.valueOf(cantOfre.getText().toString()));
                cotizacion.setPrecio(Float.valueOf(precOfre.getText().toString()));
                String dia = fechaCaduca.getText().toString();
                String dia2 = fechaOfre.getText().toString();
                cotizacion.setFecExpiracion(dia);
                cotizacion.setFecEntrega(dia2);
                cotizacion.setEstado(1);
                cotizacion.setIdEmpresaVendedora(idEmpresa);
                List<NameValuePair> cotizacionenviar = new ArrayList<NameValuePair>();
                cotizacionenviar.add(new BasicNameValuePair("idsolicitud",String.valueOf(idSolicitud)));
                cotizacionenviar.add(new BasicNameValuePair("cantofrecida",cantOfre.getText().toString()));
                cotizacionenviar.add(new BasicNameValuePair("precio",precOfre.getText().toString()));
                cotizacionenviar.add(new BasicNameValuePair("fecexpiracion",dia));
                cotizacionenviar.add(new BasicNameValuePair("fecentrega",dia2));
                cotizacionenviar.add(new BasicNameValuePair("estado",String.valueOf(1)));
                cotizacionenviar.add(new BasicNameValuePair("idempresavendedora",String.valueOf(idEmpresa)));

                JSONObject json = jsonParser.makeHttpRequest(url, "POST", cotizacionenviar);
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    int idcotizacion = json.getInt("id");
                    cotizacion.setIdCotizacion(idcotizacion);
                    cotizacionesDAO cotizar = new cotizacionesDAO(getApplicationContext());
                    cotizar.insertCotizacion(cotizacion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                finish();

            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
