package com.dragonregnan.sistemasdinamicos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.util.ArrayList;

/**
 * Created by laura on 11/02/2016.
 */
public class SolicitudesAdapter extends BaseAdapter {

    Context context;
    private ArrayList<solicitudesModel> solicitudes;

    public SolicitudesAdapter (Context context, ArrayList<solicitudesModel> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.solicitudes = data;
        Log.d("Solicitud Adapter", "Solicitud Adapter");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return solicitudes.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return solicitudes.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        empresasDAO empDAO = new empresasDAO(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.row_adaptador, null);
        TextView textTitulo = (TextView) vi.findViewById(R.id.txtvw_titulo);
        TextView textDetalle = (TextView) vi.findViewById(R.id.txtvw_detalle);

            int empresa = solicitudes.get(position).getIdEmpresaCompradora();
            String titulo = "Empresa Solicitante: "+ empDAO.getNombreEmpresa(empresa);
            String detalle = "Cantidad Solicitada: "+solicitudes.get(position).getCantSolicitada()+"/ Fecha de entrega solicitada: "+solicitudes.get(position).getFecEntregaSol();

            textTitulo.setText(titulo);
            textDetalle.setText(detalle);


        return vi;
    }
}
