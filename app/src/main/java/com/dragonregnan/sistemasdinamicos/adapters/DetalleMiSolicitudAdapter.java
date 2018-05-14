package com.dragonregnan.sistemasdinamicos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.util.ArrayList;

/**
 * Created by laura on 17/02/2016.
 */
public class DetalleMiSolicitudAdapter extends BaseAdapter {

    Context context;
    private ArrayList<cotizacionesModel> cotizaciones;

    public DetalleMiSolicitudAdapter (Context context, ArrayList<cotizacionesModel> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.cotizaciones = data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cotizaciones.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return cotizaciones.get(position);
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

        int empresa = cotizaciones.get(position).getIdEmpresaVendedora();
        String titulo = empDAO.getNombreEmpresa(empresa);
        String detalle = "Cantidad Solicitada: "+cotizaciones.get(position).getCantOfrecida() + "/ Fecha de entrega: "+ cotizaciones.get(position).getFecEntrega() + "/ Estado: " + cotizaciones.get(position).getEstado();

        textTitulo.setText(titulo);
        textDetalle.setText(detalle);

        return vi;
    }
}
