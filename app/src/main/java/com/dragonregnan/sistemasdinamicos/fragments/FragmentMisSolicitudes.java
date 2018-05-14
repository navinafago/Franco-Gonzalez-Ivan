package com.dragonregnan.sistemasdinamicos.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.activity.NuevaSolicitudActivity;
import com.dragonregnan.sistemasdinamicos.adapters.SolicitudesAdapter;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.detalles.DetalleMisSolicitudesActivity;
import com.dragonregnan.sistemasdinamicos.detalles.DetalleSolicitudesActivity;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by laura on 03/02/2016.
 */
public class FragmentMisSolicitudes extends Fragment {
    public static ArrayList<solicitudesModel> MisSolicitudes = new ArrayList<>();
    private View rootView;
    private solicitudesDAO solDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    //INSTANCIAS DE LAS CLASES DAOS
        solDAO = new solicitudesDAO(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mis_solicitudes, container, false);
        solicitudesModel soli = new solicitudesModel();
    //OBTENER EL ID DEL USUARIO LOGEADO POR MEDIO DE LAS PREFERENCIAS COMPARTIDAS
        SharedPreferences pref = getContext().getSharedPreferences("MisPreferencias", getContext().MODE_PRIVATE);
        int idEmpresa = pref.getInt("idEmpresa", 0);

        MisSolicitudes.clear();
        MisSolicitudes = solDAO.getMisSolicitudes(idEmpresa);
    //LLENAR EL LISTVIEW
        final SolicitudesAdapter adapter = new SolicitudesAdapter(this.getContext(), MisSolicitudes);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewMiSolicitud);
        listView.setAdapter(adapter);
    //MOSTRAR DETALLE DE LA POSICION SELECCONADA
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int item = (int) adapter.getItemId(position);
                int idsol = MisSolicitudes.get(item).getIdSolicitud();
                int idempcompradora = MisSolicitudes.get(item).getIdEmpresaCompradora();
                int cantidad = MisSolicitudes.get(item).getCantSolicitada();
                String fecha = String.valueOf(MisSolicitudes.get(item).getFecEntregaSol());

                Intent i = new Intent(getActivity(), DetalleMisSolicitudesActivity.class);
                i.putExtra("idSol", idsol);
                i.putExtra("idEmpCompradora", idempcompradora);
                i.putExtra("Cantidad", cantidad);
                i.putExtra("Fecha", fecha);
                startActivity(i);
            }

        });
    //MANDAR AL ACTIVITY QUE CREA UNA NUEVA SOLICITUD
        Button nueva = (Button) rootView.findViewById(R.id.solicitu_nueva);
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NuevaSolicitudActivity.class);
                startActivity(i);
            }
        });

        return rootView;

    }


}
