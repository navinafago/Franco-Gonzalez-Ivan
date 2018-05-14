package com.dragonregnan.sistemasdinamicos.detalles;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;

/**
 * Created by laura on 29/02/2016.
 */
public class DetalleVenta extends ActionBarActivity {
    private String nombreComp;
    private String nombreVend;
    private int cantidad;
    private float precio;
    private float total;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_venta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

     //OBTENER EXTRAS DEL ACTIVITY PADRE

        Bundle extras = getIntent().getExtras();
        nombreComp = extras.getString("NombreCompradora");
        nombreVend = extras.getString("NombreVendedora");
        cantidad = extras.getInt("Cantidad");
        precio = extras.getFloat("Precio");
        total = extras.getFloat("Total");

    //MOSTRAR DATOS DE LA VENTA

        TextView comprador = (TextView) findViewById(R.id.empresacompradora_historial);
        TextView vendedor = (TextView) findViewById(R.id.empresavendedora_historial);
        TextView cantidad = (TextView) findViewById(R.id.txtvw_cantidadmicot);
        TextView precio = (TextView) findViewById(R.id.txtvw_preciomicot);
        TextView total = (TextView) findViewById(R.id.txtvw_totalmicot);

        comprador.setText("Compró: "+nombreComp);
        vendedor.setText("Vendió: "+nombreVend);
        cantidad.setText("Cantidad Ofrecida: "+cantidad);
        precio.setText("Precio: "+precio+" x pieza");
        total.setText("Total: $ "+total);

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
