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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.JSON.JSONParser;
import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.balancesDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasDAO;
import com.dragonregnan.sistemasdinamicos.dao.comprasOperacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasPenalizadasDAO;
import com.dragonregnan.sistemasdinamicos.dao.encadenamientosDAO;
import com.dragonregnan.sistemasdinamicos.dao.pagosDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.comprasOperacionesModel;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.pagosModel;

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
 * Created by laura on 17/02/2016.
 */
public class DetalleCotizacionActivity extends ActionBarActivity {
    private int idCotizacion;
    private int idSolicitud;
    private int idEmpresaVendedora;
    private int CantidadOfrecida;
    private float Precio;
    private String FechaEntrega;
    private String FechaExpiracion;
    private int Estado;
    private cotizacionesDAO cotDAO;
    private comprasDAO comDAO;
    private solicitudesDAO solDAO;
    private comprasOperacionesDAO comOpeDAO;
    private int idEmpresaCompradora;
    private pagosDAO pagoDAO;
    private balancesDAO balDAO;
    private empresasDAO empresaDAO;
    private encadenamientosDAO encadenamientoDAO;
    private empresasPenalizadasDAO empPena;
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

    //INSTANCIAR DAOS

        cotDAO = new cotizacionesDAO(this);
        comDAO = new comprasDAO(this);
        solDAO = new solicitudesDAO(this);
        comOpeDAO = new comprasOperacionesDAO(this);
        pagoDAO = new pagosDAO(this);
        balDAO = new balancesDAO(this);
        empresaDAO = new empresasDAO(this);
        encadenamientoDAO = new encadenamientosDAO(this);
        empPena = new empresasPenalizadasDAO(this);

    //OBTENER EXTRAS DEL ACTIVITY PADRE

        Bundle extras = getIntent().getExtras();
        idCotizacion = extras.getInt("IdCot");
        idSolicitud = extras.getInt("IdSol");
        idEmpresaVendedora = extras.getInt("IdEmpVendedora");
        CantidadOfrecida = extras.getInt("CantOfrecida");
        FechaEntrega = extras.getString("FecEntrega");
        FechaExpiracion = extras.getString("FecExpiracion");
        Estado = extras.getInt("Estado");
        Precio = extras.getFloat("Precio");

    //MOSTRAR DATOS DE LA COTIZACION

        TextView empresaVen = (TextView) findViewById(R.id.empresavendedora);
        TextView cantOfrecida = (TextView) findViewById(R.id.txtvw_cantidadcot);
        TextView precio = (TextView) findViewById(R.id.txtvw_preciocot);
        final TextView total = (TextView) findViewById(R.id.txtvw_totalcot);
        TextView fecEntrega = (TextView) findViewById(R.id.txtvw_fechacot);
        TextView fecExpiracion = (TextView) findViewById(R.id.txtvw_fechacotexp);
        TextView penalizaciones = (TextView) findViewById(R.id.empresavenrep);

        empresaVen.setText(empresaDAO.getNombreEmpresa(idEmpresaVendedora));
        cantOfrecida.setText("Cantidad Ofrecida: "+CantidadOfrecida);
        precio.setText("Precio: "+Precio);
        final float tot = Precio * CantidadOfrecida;
        total.setText("Total: "+tot);
        fecEntrega.setText("Fecha de entrega y pago ofrecida: "+fecEntrega);
        fecExpiracion.setText("Fecha de expiracion de la oferta: "+fecExpiracion);
        penalizaciones.setText("Penalizaciones de la empresa Vendedora: "+ empPena.getPenalizaciones(idEmpresaVendedora));

    //AÑADIR FUNCIONALIDAD A BOTONES

        final Button bt = (Button) findViewById(R.id.cotizacion_aceptar);
        final Button bt2 = (Button) findViewById(R.id.cotizacion_rechazar);
        final Button bt3 = (Button) findViewById(R.id.cotizacion_pagar);

        //COMPROBAR EL ESTADO DE LACOTIZACION Y EN SU CASO DE LA COMPRA
        comprasModel comprobar = new comprasModel();
        comprobar = comDAO.getCompra(idCotizacion);
        if(Estado == 1){
            bt.setVisibility(View.VISIBLE);
            bt2.setVisibility(View.VISIBLE);
            bt3.setVisibility(View.INVISIBLE);
        }else if(Estado == 2 ||comDAO.existsCompra(idCotizacion)== true ){
            bt.setVisibility(View.INVISIBLE);
            bt2.setVisibility(View.INVISIBLE);
            bt3.setVisibility(View.VISIBLE);
        }else if(Estado == 3 || Estado == 4 || Estado == 5 || comprobar.getLiquidada()== true){
            bt.setVisibility(View.INVISIBLE);
            bt2.setVisibility(View.INVISIBLE);
            bt3.setVisibility(View.INVISIBLE);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Cliente acepta la cotizacion convirtiendola en una venta

                int estadonuevo = 2;
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
                comprasModel compra = new comprasModel();
                compra.setIdCotizacion(idCotizacion);
                compra.setEntregada(false);
                compra.setLiquidada(false);
                Calendar cal = Calendar.getInstance();
                compra.setFecCompra(String.valueOf(cal.getTime()));

                comDAO.insertCompras(compra);
            //ABONAR A CUENTA DE CLIENTES DEL VENDEDOR
                Float salCli = balDAO.getSaldo(idEmpresaVendedora,7);
                balancesModel clientes = new balancesModel();
                clientes.setIdEmpresa(idEmpresaVendedora);
                clientes.setIdCuenta(7);
                clientes.setFecBalance(String.valueOf(cal.getTime()));

                float clientesTotal = salCli - Float.valueOf(total.getText().toString());
                clientes.setSaldo(clientesTotal);

                balDAO.insertCuentaBalance(clientes);


                bt.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.VISIBLE);

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Cliente rechaza la cotizacion

                int estadonuevo = 3;
                cotDAO.updateCotizacion(idCotizacion,estadonuevo);
                List<NameValuePair> cotUpdate = new ArrayList<NameValuePair>();
                cotUpdate.add(new BasicNameValuePair("idCotizacion",String.valueOf(idCotizacion)));
                cotUpdate.add(new BasicNameValuePair("estado",String.valueOf(estadonuevo)));

                JSONObject jsoncot = jsonParser.makeHttpRequest(url4, "POST", cotUpdate);
                try {
                    int success = jsoncot.getInt(TAG_SUCCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bt.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.INVISIBLE);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //Realiza el pago de la venta el cliente al vendedor

                idEmpresaCompradora = solDAO.getIdEmpCompradora(idSolicitud);
                comprasModel compra = new comprasModel();
                compra = comDAO.getCompra(idCotizacion);
                comprasOperacionesModel operacion = new comprasOperacionesModel();
                operacion.setIdTipoOperacion(2);
                operacion.setIdCompra(compra.getIdCompra());
                operacion.setIdEmpresaVendedora(idEmpresaVendedora);
                operacion.setIdEmpresaCompradora(idEmpresaCompradora);

                List<NameValuePair> operaciones = new ArrayList<NameValuePair>();
                operaciones.add(new BasicNameValuePair("idtipooperacion",String.valueOf(2)));
                operaciones.add(new BasicNameValuePair("idcompra",String.valueOf(compra.getIdCompra())));
                operaciones.add(new BasicNameValuePair("idempresacompradora",String.valueOf(idEmpresaVendedora)));
                operaciones.add(new BasicNameValuePair("idempresavendedora",String.valueOf(idEmpresaCompradora)));
                int idOperacion=0;

                JSONObject json = jsonParser.makeHttpRequest(url, "POST", operaciones);
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    int idoperacion = json.getInt("id");
                    operacion.setIdOperacion(idoperacion);
                    idOperacion = idOperacion = comOpeDAO.insertCompraOperacion(operacion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Float salBan = balDAO.getSaldo(idEmpresaCompradora, 5);
                Float salCli = balDAO.getSaldo(idEmpresaVendedora, 7);
                Float salBanVen = balDAO.getSaldo(idEmpresaVendedora,7);

                Float proveedor1 = balDAO.getSaldo(idEmpresaCompradora,8);
                Float proveedor2 = balDAO.getSaldo(idEmpresaCompradora,9);

                Float almacen1 = balDAO.getSaldo(idEmpresaCompradora,1);
                Float almacen2 = balDAO.getSaldo(idEmpresaCompradora,2);

                if( salBan > Float.valueOf(total.getText().toString()) ){
                    pagosModel pago = new pagosModel();
                    pago.setIdOperacion(idOperacion);
                    pago.setCantidadPagada(Float.valueOf(total.getText().toString()));
                    Calendar cal = Calendar.getInstance();
                    String date1=String.valueOf(cal.getTime());
                    pago.setFecPago(date1);

                    List<NameValuePair> pagos = new ArrayList<NameValuePair>();
                    pagos.add(new BasicNameValuePair("idoperacion",String.valueOf(idOperacion)));
                    pagos.add(new BasicNameValuePair("cantidadpagada", String.valueOf(Float.valueOf(total.getText().toString()))));
                    pagos.add(new BasicNameValuePair("fecpago", String.valueOf(date1)));

                    JSONObject json2 = jsonParser.makeHttpRequest(url2, "POST", pagos);
                    try {
                        int success = json2.getInt(TAG_SUCCESS);
                        int idembarque = json2.getInt("id");
                        pago.setIdPago(idembarque);
                        pagoDAO.insertPago(pago);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    balancesModel bancos = new balancesModel();
                    balancesModel clientes = new balancesModel();
                    balancesModel bancos2 = new balancesModel();
                    balancesModel proveedores = new balancesModel();

                //Movimiento en cuentas del balance empresa vendedora
                    bancos.setIdEmpresa(idEmpresaVendedora);
                    bancos.setIdCuenta(5);
                    bancos.setFecBalance(String.valueOf(cal.getTime()));

                    float saldototalban = salBanVen + Float.valueOf(total.getText().toString());
                    bancos.setSaldo(saldototalban);

                    balDAO.insertCuentaBalance(bancos);

                    clientes.setIdEmpresa(idEmpresaVendedora);
                    clientes.setIdCuenta(7);
                    clientes.setFecBalance(String.valueOf(cal.getTime()));

                    float clientesTotal = salCli - Float.valueOf(total.getText().toString());
                    clientes.setSaldo(clientesTotal);

                    balDAO.insertCuentaBalance(clientes);

                //Movimiento en cuentas del balance empresa compradora
                    bancos2.setIdEmpresa(idEmpresaCompradora);
                    bancos2.setIdCuenta(5);
                    bancos2.setFecBalance(String.valueOf(cal.getTime()));

                    float saldototalban2 = salBan - Float.valueOf(total.getText().toString());
                    bancos2.setSaldo(saldototalban2);

                    balDAO.insertCuentaBalance(bancos);

                    int idIndustriaEmpCom = empresaDAO.getIndustriaEmpresa(idEmpresaCompradora);
                    int idIndustriaSolicitud = solDAO.getIdIndustria(idSolicitud);
                    ArrayList<Integer> encadenamiento =  encadenamientoDAO.getIndustriasVenden(idIndustriaEmpCom);

                    if(encadenamiento.get(0).intValue() > encadenamiento.get(1).intValue()){
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){
                            proveedores.setIdEmpresa(idEmpresaCompradora);
                            proveedores.setIdCuenta(9);
                            proveedores.setFecBalance(String.valueOf(cal.getTime()));

                            float proveedoresTotal = proveedor2 - Float.valueOf(total.getText().toString());
                            proveedores.setSaldo(proveedoresTotal);

                            balDAO.insertCuentaBalance(proveedores);
                        }
                    }else{
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){
                            proveedores.setIdEmpresa(idEmpresaCompradora);
                            proveedores.setIdCuenta(8);
                            proveedores.setFecBalance(String.valueOf(cal.getTime()));
                            float proveedoresTotal = proveedor1 - Float.valueOf(total.getText().toString());
                            proveedores.setSaldo(proveedoresTotal);
                            balDAO.insertCuentaBalance(proveedores);

                        }
                    }
                    balancesModel almacencuenta = new balancesModel();
                    if(encadenamiento.get(0).intValue() > encadenamiento.get(1).intValue()){
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){

                            almacencuenta.setIdEmpresa(idEmpresaCompradora);
                            almacencuenta.setIdCuenta(2);
                            almacencuenta.setFecBalance(String.valueOf(cal.getTime()));
                            float almacenesTotal = almacen2 + tot;
                            almacencuenta.setSaldo(almacenesTotal);

                            balDAO.insertCuentaBalance(almacencuenta);
                        }
                    }else{
                        if(encadenamiento.get(0).intValue()==idIndustriaSolicitud){
                            almacencuenta.setIdEmpresa(idEmpresaCompradora);
                            almacencuenta.setIdCuenta(1);
                             almacencuenta.setFecBalance(String.valueOf(cal.getTime()));
                            float almacenesTotal = almacen1 + tot;
                            almacencuenta.setSaldo(almacenesTotal);
                            balDAO.insertCuentaBalance(almacencuenta);

                        }
                    }
                //CAMBIAR ESTADO DE LA COMPRA A LIQUIDADA
                    comprasModel compraModificar= new comprasModel();
                    compraModificar = comDAO.getCompra(idCotizacion);
                    comDAO.UpdateLiquidacion(compraModificar.getIdCompra(),1);
                    List<NameValuePair> compraupdate = new ArrayList<NameValuePair>();
                    compraupdate.add(new BasicNameValuePair("idCompra",String.valueOf(compraModificar.getIdCompra())));
                    compraupdate.add(new BasicNameValuePair("opcion",String.valueOf(2)));
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
                        cotDAO.updateCotizacion(idCotizacion,estadonuevo);
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
                //DESAPARAECER LOS BOTONES PARA IMPEDIR ACCIONES
                    bt.setVisibility(View.INVISIBLE);
                    bt2.setVisibility(View.INVISIBLE);
                    bt3.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "No tienes sufciente dinero para realizar el pago", Toast.LENGTH_LONG);
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
