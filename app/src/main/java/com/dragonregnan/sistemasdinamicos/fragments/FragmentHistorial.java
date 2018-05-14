package com.dragonregnan.sistemasdinamicos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.adapters.HistorialAdapter;
import com.dragonregnan.sistemasdinamicos.adapters.SolicitudesAdapter;
import com.dragonregnan.sistemasdinamicos.dao.comprasDAO;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.detalles.DetalleSolicitudesActivity;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;

import java.util.ArrayList;

/**
 * Created by laura on 03/02/2016.
 */
public class FragmentHistorial extends Fragment {
    public static ArrayList<comprasModel> compras = new ArrayList<>();
    private comprasDAO comDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    //INSTANCIAS DE LAS CLASES DAOS
        comDAO = new comprasDAO(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial, container, false);

        compras.clear();
        compras = comDAO.getcompras();
    //LLENAR EL LISTVIEW
        final HistorialAdapter adapter = new HistorialAdapter(this.getContext(), compras);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewHistorial);
        listView.setAdapter(adapter);
    //MOSTRAR DETALLE DE LA POSICION SELECCONADA
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = (int) adapter.getItemId(position);

                empresasDAO empDAO = new empresasDAO(getContext());
                cotizacionesDAO cotdao = new cotizacionesDAO(getContext());
                solicitudesDAO solDAO = new solicitudesDAO(getContext());

                int idSolicitud = cotdao.getIdSolicitud(compras.get(item).getIdCotizacion());
                int idempresaCompradora = solDAO.getIdEmpCompradora(idSolicitud);
                int idempresaVendedora = cotdao.getIdEmpresaVendedora(compras.get(item).getIdCotizacion());
                String empresaCompradora = empDAO.getNombreEmpresa(idempresaCompradora);
                String empresaVendedora = empDAO.getNombreEmpresa(idempresaVendedora);

                cotizacionesModel cotizacion = new cotizacionesModel();
                cotizacion = cotdao.getCotizacion(compras.get(item).getIdCotizacion());

                float total = cotizacion.getCantOfrecida() * cotizacion.getPrecio();

                Intent i = new Intent(getActivity(), DetalleSolicitudesActivity.class);
                i.putExtra("NombreCompradora",empresaCompradora);
                i.putExtra("NombreVendedora", empresaVendedora);
                i.putExtra("Cantidad",cotizacion.getCantOfrecida());
                i.putExtra("Precio", cotizacion.getPrecio());
                i.putExtra("Total",total);
                startActivity(i);
            }

        });

        return rootView;
    }

}
