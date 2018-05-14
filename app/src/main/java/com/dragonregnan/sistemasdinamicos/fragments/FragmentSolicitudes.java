package com.dragonregnan.sistemasdinamicos.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.adapters.SolicitudesAdapter;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.detalles.DetalleSolicitudesActivity;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by laura on 03/02/2016.
 */
public class FragmentSolicitudes extends Fragment {
    public static ArrayList<solicitudesModel> solicitudes = new ArrayList<>();
    private View rootView;
    private solicitudesDAO solDAO;
    private empresasDAO empDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    //INSTANCIAS DE LAS CLASES DAOS
        solDAO = new solicitudesDAO(this.getContext());
        empDAO = new empresasDAO(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solicitudes, container, false);
    //OBTENER EL ID DEL USUARIO LOGEADO POR MEDIO DE LAS PREFERENCIAS COMPARTIDAS
        SharedPreferences pref = getContext().getSharedPreferences("MisPreferencias", getContext().MODE_PRIVATE);
        int idEmpresa = pref.getInt("idEmpresa", 0);

        int idIndustria = empDAO.getIndustriaEmpresa(idEmpresa);

        solicitudes.clear();
        solicitudes = solDAO.getSolicitudes(idIndustria);
    //LLENAR EL LISTVIEW
        final SolicitudesAdapter adapter = new SolicitudesAdapter(this.getContext(), solicitudes);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewSolicitud);
        listView.setAdapter(adapter);
    //MOSTRAR DETALLE DE LA POSICION SELECCONADA
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int item = (int) adapter.getItemId(position);
                int idsol= solicitudes.get(item).getIdSolicitud();
                int idempcompradora = solicitudes.get(item).getIdEmpresaCompradora();
                int cantidad = solicitudes.get(item).getCantSolicitada();
                String fecha = String.valueOf(solicitudes.get(item).getFecEntregaSol());

                Intent i = new Intent(getActivity(), DetalleSolicitudesActivity.class);
                i.putExtra("idSol",idsol);
                i.putExtra("idEmpCompradora", idempcompradora);
                i.putExtra("Cantidad",cantidad);
                i.putExtra("Fecha",fecha);
                startActivity(i);
            }

        });
        return rootView;

    }


}
