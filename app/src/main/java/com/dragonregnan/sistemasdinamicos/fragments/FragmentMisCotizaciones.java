package com.dragonregnan.sistemasdinamicos.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.adapters.DetalleMiSolicitudAdapter;
import com.dragonregnan.sistemasdinamicos.adapters.MisCotizacionesAdapter;
import com.dragonregnan.sistemasdinamicos.adapters.MisSolicitudesAdapter;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.detalles.DetalleCotizacionActivity;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.util.ArrayList;

/**
 * Created by laura on 03/02/2016.
 */
public class FragmentMisCotizaciones extends Fragment {
    public static ArrayList<cotizacionesModel> cotizaciones = new ArrayList<>();
    private View rootView;
    private cotizacionesDAO cotDAO;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    //INSTANCIAS DE LAS CLASES DAOS
        cotDAO = new cotizacionesDAO(this.getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_miscotizaciones, container, false);
        ListView listcotizaciones = (ListView) rootView.findViewById(R.id.listViewMiCotizacion);
    //OBTENER EL ID DEL USUARIO LOGEADO POR MEDIO DE LAS PREFERENCIAS COMPARTIDAS
        SharedPreferences pref = getContext().getSharedPreferences("MisPreferencias", getContext().MODE_PRIVATE);
        int idEmpresa = pref.getInt("idEmpresa", 0);

        cotizaciones.clear();
        cotizaciones= cotDAO.getCotizaciones(idEmpresa);
    //LLENAR EL LISTVIEW
        final MisCotizacionesAdapter adapter = new MisCotizacionesAdapter(getContext(), cotizaciones);
        listcotizaciones.setAdapter(adapter);
    //MOSTRAR DETALLE DE LA POSICION SELECCONADA
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

                Intent i = new Intent(getActivity(), DetalleCotizacionActivity.class);
                i.putExtra("IdCot", idcot);
                i.putExtra("IdSol", idsol);
                i.putExtra("IdEmpVendedora", idempvendedora);
                i.putExtra("CantOfrecida", cantofrecida);
                i.putExtra("FecEntrega", fecentrega);
                i.putExtra("FecExpiracion", fecexpiracion);
                i.putExtra("Estado", estado);
                i.putExtra("Precio", precio);
                startActivity(i);

            }
        });
        return rootView;
    }
}
