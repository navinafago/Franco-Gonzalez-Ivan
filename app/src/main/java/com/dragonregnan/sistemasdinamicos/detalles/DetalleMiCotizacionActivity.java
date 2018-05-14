package com.dragonregnan.sistemasdinamicos.detalles;

import android.annotation.TargetApi;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.JSON.JSONParser;
import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.almacenesDAO;
import com.dragonregnan.sistemasdinamicos.dao.balancesDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasOperacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.embarquesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.encadenamientosDAO;
import com.dragonregnan.sistemasdinamicos.dao.nivelesVariablesDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.comprasOperacionesModel;
import com.dragonregnan.sistemasdinamicos.model.embarquesModel;
import com.dragonregnan.sistemasdinamicos.model.nivelesVariablesModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by laura on 18/02/2016.
 */
public class DetalleMiCotizacionActivity extends ActionBarActivity {
    private int idCotizacion;
    private int idSolicitud;
    private int idEmpresaCompradora;
    private int idEmpresaVendedora;
    private int CantidadOfrecida;
    private float Precio;
    private String FechaEntrega;
    private String FechaExpiracion;
    private int Estado;
    private cotizacionesDAO cotDAO;
    private comprasDAO comDAO;
    private solicitudesDAO solDAO;
    private almacenesDAO almaDAO;
    private embarquesDAO embDAO;
    private comprasOperacionesDAO comOpeDAO;
    private nivelesVariablesDAO nivelesDAO;
    private empresasDAO empDAO;
    private encadenamientosDAO encadenamientoDAO;
    private balancesDAO balDAO;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://ultragalaxia.com/android/insertoperaciones.php";
    private static String url2 = "http://ultragalaxia.com/android/insertembarque.php";
    private static String url3 = "http://ultragalaxia.com/android/updateCompra.php";
    private static String url4 = "http://ultragalaxia.com/android/updateCotizacion.php";
    private static final String TAG_SUCCESS = "success";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_solicitudes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        cotDAO = new cotizacionesDAO(this);
        comDAO = new comprasDAO(this);
        solDAO = new solicitudesDAO(this);
        almaDAO = new almacenesDAO(this);
        embDAO = new embarquesDAO(this);
        comOpeDAO = new comprasOperacionesDAO(this);
        nivelesDAO = new nivelesVariablesDAO(this);
        empDAO = new empresasDAO(this);
        balDAO = new balancesDAO(this);
        encadenamientoDAO = new encadenamientosDAO(this);

    //OBTENER EXTRAS DEL ACTIVITY PADRE

        Bundle extras = getIntent().getExtras();
        idEmpresaCompradora = extras.getInt("IdEmpCompradora");
        idCotizacion = extras.getInt("IdCot");
        idSolicitud = extras.getInt("IdSol");
        idEmpresaVendedora = extras.getInt("IdEmpVendedora");
        CantidadOfrecida = extras.getInt("CantOfrecida");
        FechaEntrega = extras.getString("FecEntrega");
        FechaExpiracion = extras.getString("FecExpiracion");
        Estado = extras.getInt("Estado");
        Precio = extras.getFloat("Precio");

    //MOSTRAR DATOS DE LA COTIZACION
        TextView empresaVen = (TextView) findViewById(R.id.empresavendedoramicot);
        final TextView cantOfrecida = (TextView) findViewById(R.id.txtvw_cantidadmicot);
        TextView precio = (TextView) findViewById(R.id.txtvw_preciomicot);
        TextView total = (TextView) findViewById(R.id.txtvw_totalmicot);
        TextView fecEntrega = (TextView) findViewById(R.id.txtvw_fechacot);
        TextView fecExpiracion = (TextView) findViewById(R.id.txtvw_fechacotvencida);

        cantOfrecida.setText("Cantidad Ofrecida: "+CantidadOfrecida);
        precio.setText("Precio: "+Precio);
        final float tot = Precio * CantidadOfrecida;
        total.setText("Total: "+tot);
        fecEntrega.setText("Fecha de entrega y pago ofrecida: " + fecEntrega);
        fecExpiracion.setText("Fecha de expiracion de la oferta: " + fecExpiracion);
        empresaVen.setText(empDAO.getNombreEmpresa(idEmpresaVendedora));

        comprasModel compra = new comprasModel();
        compra = comDAO.getCompra(idCotizacion);

    //CAMBIAR VISIBILIDAD DEL BOTON
        final Button bt = (Button) findViewById(R.id.micotEnviarPedido);
        if(comDAO.existsCompra(idCotizacion) == false || compra.getEntregada()== true){
            bt.setVisibility(View.INVISIBLE);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comprasModel compra1 = new comprasModel();
                compra1 = comDAO.getCompra(idCotizacion);


                int idMercancias = almaDAO.getIdAlmacen(idEmpresaVendedora, 4);
                if( nivelesDAO.getActual(idEmpresaVendedora,idMercancias) >= CantidadOfrecida ){
                // CREA LA OPERACION DEL EMBARQUE
                    comprasOperacionesModel operacion = new comprasOperacionesModel();
                    operacion.setIdTipoOperacion(1);
                    operacion.setIdCompra(compra1.getIdCompra());
                    operacion.setIdEmpresaVendedora(idEmpresaVendedora);
                    operacion.setIdEmpresaCompradora(idEmpresaCompradora);

                    List<NameValuePair> operaciones = new ArrayList<NameValuePair>();
                    operaciones.add(new BasicNameValuePair("idtipooperacion",String.valueOf(1)));
                    operaciones.add(new BasicNameValuePair("idcompra",String.valueOf(compra1.getIdCompra())));
                    operaciones.add(new BasicNameValuePair("idempresacompradora",String.valueOf(idEmpresaVendedora)));
                    operaciones.add(new BasicNameValuePair("idempresavendedora",String.valueOf(idEmpresaCompradora)));
                    int idOperacion=0;

                    JSONObject json = jsonParser.makeHttpRequest(url, "POST", operaciones);
                    try {
                        int success = json.getInt(TAG_SUCCESS);
                        int idoperacion = json.getInt("id");
                        operacion.setIdOperacion(idoperacion);
                        idOperacion = comOpeDAO.insertCompraOperacion(operacion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int nuevoactual = nivelesDAO.getActual(idEmpresaVendedora,4)-CantidadOfrecida;

                // GENERAR EL EMBARQUE CON EL ID DE LA OPERACION
                    embarquesModel embarque = new embarquesModel();
                    embarque.setIdOperacion(idOperacion);
                    embarque.setCantidadEmbarcada(CantidadOfrecida);

                    Calendar cal = Calendar.getInstance();
                    String date1=String.valueOf(cal.getTime());
                    embarque.setFecEmbarque(date1);

                    List<NameValuePair> embarques = new ArrayList<NameValuePair>();
                    embarques.add(new BasicNameValuePair("idoperacion",String.valueOf(idOperacion)));
                    embarques.add(new BasicNameValuePair("cantidadembarcada",String.valueOf(CantidadOfrecida)));
                    embarques.add(new BasicNameValuePair("fecembarque",date1));

                    JSONObject json2 = jsonParser.makeHttpRequest(url2, "POST", embarques);
                    try {
                        int success = json2.getInt(TAG_SUCCESS);
                        int idembarque = json2.getInt("id");
                        embarque.setIdEmbarque(idembarque);
                        embDAO.insertEmbarque(embarque);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                // ACTUALIZAR EL ACTUAL DE lAS MERCANCIAS DEL VENDEDOR Y CUENTA DEL BALANCE DE MERCANCIAS A CLIENTES
                    int idalmacenMercancias = almaDAO.getIdAlmacen(idEmpresaVendedora, 4);
                    float coeficiente = (balDAO.getSaldo(idEmpresaVendedora,4)/nivelesDAO.getActual(idEmpresaVendedora,idalmacenMercancias));
                    balancesModel mercanciasCuenta = new balancesModel();
                    mercanciasCuenta.setIdCuenta(4);
                    mercanciasCuenta.setIdEmpresa(idEmpresaVendedora);
                    mercanciasCuenta.setSaldo(balDAO.getSaldo(idEmpresaVendedora,4)- (CantidadOfrecida*coeficiente));
                    mercanciasCuenta.setFecBalance(String.valueOf(cal.getTime()));

                    balDAO.insertCuentaBalance(mercanciasCuenta);

                    balancesModel clientesCuenta = new balancesModel();
                    clientesCuenta.setIdCuenta(7);
                    clientesCuenta.setIdEmpresa(idEmpresaVendedora);
                    clientesCuenta.setSaldo(balDAO.getSaldo(idEmpresaVendedora, 7) + (CantidadOfrecida * coeficiente));
                    clientesCuenta.setFecBalance(String.valueOf(cal.getTime()));

                    balDAO.insertCuentaBalance(clientesCuenta);

                    nivelesVariablesModel nivelcambiar = new nivelesVariablesModel();
                    nivelcambiar = nivelesDAO.getNivel(idEmpresaVendedora,4);
                    nivelcambiar.setActual(nuevoactual);
                    nivelesDAO.insertNivel(nivelcambiar);

                //ACTUALIZAR ALMACEN DEL CLIENTE
                    int idIndustriaEmpCom = empDAO.getIndustriaEmpresa(idEmpresaCompradora);
                    int idIndustriaSolicitud = solDAO.getIdIndustria(idSolicitud);
                    ArrayList<Integer> encadenamiento =  encadenamientoDAO.getIndustriasVenden(idIndustriaEmpCom);

                    if(encadenamiento.get(0).intValue() > encadenamiento.get(1).intValue()){
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){
                            int idalmacen = almaDAO.getIdAlmacen(idEmpresaCompradora,2);
                            int almacen2 = nivelesDAO.getActual(idEmpresaCompradora, idalmacen);
                            int nuevo = almacen2 + CantidadOfrecida;
                            nivelesVariablesModel actualizarNivel = new nivelesVariablesModel();
                            actualizarNivel = nivelesDAO.getNivel(idEmpresaCompradora,idalmacen);
                            actualizarNivel.setActual(nuevo);
                            nivelesDAO.insertNivel(actualizarNivel);
                        }
                    }else{
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){
                            int idalmacen = almaDAO.getIdAlmacen(idEmpresaCompradora,1);
                            int almacen1 = nivelesDAO.getActual(idEmpresaCompradora, idalmacen);
                            int nuevo = almacen1 + CantidadOfrecida;
                            nivelesVariablesModel actualizarNivel = new nivelesVariablesModel();
                            actualizarNivel = nivelesDAO.getNivel(idEmpresaCompradora,idalmacen);
                            actualizarNivel.setActual(nuevo);
                            nivelesDAO.insertNivel(actualizarNivel);
                        }
                    }

                // ACTUALIZAR LA COMPRA A ENTREGADA

                    comDAO.UpdateEntregada(compra1.getIdCompra(),1);
                    List<NameValuePair> compraupdate = new ArrayList<NameValuePair>();
                    compraupdate.add(new BasicNameValuePair("idCompra",String.valueOf(compra1.getIdCompra())));
                    compraupdate.add(new BasicNameValuePair("opcion",String.valueOf(1)));
                    compraupdate.add(new BasicNameValuePair("valor",String.valueOf(1)));

                    JSONObject jsoncompra = jsonParser.makeHttpRequest(url3, "POST", compraupdate);
                    try {
                        int success = jsoncompra.getInt(TAG_SUCCESS);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    comprasModel compraComprobar= new comprasModel();
                    compraComprobar = comDAO.getCompra(idCotizacion);
                    //VERIFICAR SI LA COMPRA SE PUEDE DAR POR TERMINADA
                    if(compraComprobar.getLiquidada()== true && compraComprobar.getEntregada() == true){
                        int estadonuevo = 4;
                        cotDAO.updateCotizacion(idCotizacion, estadonuevo);
                        List<NameValuePair> cotUpdate = new ArrayList<NameValuePair>();
                        cotUpdate.add(new BasicNameValuePair("idCotizacion",String.valueOf(idCotizacion)));
                        cotUpdate.add(new BasicNameValuePair("estado",String.valueOf(estadonuevo)));

                        JSONObject jsoncot = jsonParser.makeHttpRequest(url4, "POST", cotUpdate);
                        try {
                            int success = jsoncot.getInt(TAG_SUCCESS);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    bt.setVisibility(View.INVISIBLE);
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No cuentas con las unidades suficientes para hacer el envío", Toast.LENGTH_LONG);
                    toast.show();
                }





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
