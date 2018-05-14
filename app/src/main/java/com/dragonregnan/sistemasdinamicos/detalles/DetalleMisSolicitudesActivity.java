package com.dragonregnan.sistemasdinamicos.detalles;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.adapters.DetalleMiSolicitudAdapter;
import com.dragonregnan.sistemasdinamicos.adapters.SolicitudesAdapter;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by laura on 17/02/2016.
 */
public class DetalleMisSolicitudesActivity extends ActionBarActivity {

    public static ArrayList<cotizacionesModel> cotizaciones = new ArrayList<>();
    private int idSolicitud;
    private int idEmpresaCompradora;
    private int CantidadSolicitada;
    private String fechaSolicitada;

    private cotizacionesDAO cotDAO;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_misolicitud);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        cotDAO = new cotizacionesDAO(this);

    //  OBTENER EXTRAS DEL ACTIVITY PADRE
        Bundle extras = getIntent().getExtras();
        idSolicitud = extras.getInt("idSol");
        idEmpresaCompradora = extras.getInt("idEmpCompradora");
        CantidadSolicitada = extras.getInt("Cantidad");
        fechaSolicitada = extras.getString("Fecha");

    //MOSTRAR DATOS DE LA SOLICITUD

        TextView empresaSol = (TextView) findViewById(R.id.txtvw_Miempresa);
        TextView cantidadSolicitada = (TextView) findViewById(R.id.txtvw_Micantidad);
        TextView fecha = (TextView) findViewById(R.id.txtvw_Mifecha);
        ListView listcotizaciones = (ListView) findViewById(R.id.listView_detalle_misolicitud);

        cantidadSolicitada.setText("Cantidad Solicitada: "+ String.valueOf(CantidadSolicitada));
        fecha.setText("Fecha de Entrega: " + fechaSolicitada);
        cotizaciones= cotDAO.getCotizacionesSolicitud(idSolicitud);

    // SE MUESTRA EL DETALLE DE LA COTIZACION
        final DetalleMiSolicitudAdapter adapter = new DetalleMiSolicitudAdapter(this, cotizaciones);
        listcotizaciones.setAdapter(adapter);
        listcotizaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int item = (int) adapter.getItemId(position);
                int idcot = cotizaciones.get(item).getIdCotizacion();
                int idsol = cotizaciones.get(item).getIdSolicitud();
                int idempvendedora = cotizaciones.get(item).getIdEmpresaVendedora();
                int cantofrecida = cotizaciones.get(item).getCantOfrecida();
                String fecentrega = String.valueOf(cotizaciones.get(item).getFecEntrega());
                String fecexpiracion = String.valueOf(cotizaciones.get(item).getFecExpiracion());
                int estado = cotizaciones.get(item).getEstado();
                float precio = cotizaciones.get(item).getPrecio();

                Intent i = new Intent(DetalleMisSolicitudesActivity.this, DetalleCotizacionActivity.class);
                i.putExtra("IdCot", idcot);
                i.putExtra("IdSol", idsol);
                i.putExtra("IdEmpVendedora", idempvendedora);
                i.putExtra("IdEmpCompradora", idEmpresaCompradora);
                i.putExtra("CantOfrecida", cantofrecida);
                i.putExtra("FecEntrega", fecentrega);
                i.putExtra("FecExpiracion", fecexpiracion);
                i.putExtra("Estado", estado);
                i.putExtra("Precio", precio);
                startActivity(i);

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
