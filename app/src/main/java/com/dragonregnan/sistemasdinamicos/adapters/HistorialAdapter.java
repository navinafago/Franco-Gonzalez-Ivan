package com.dragonregnan.sistemasdinamicos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.cotizacionesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.solicitudesDAO;
import com.dragonregnan.sistemasdinamicos.model.comprasModel;
import com.dragonregnan.sistemasdinamicos.model.cotizacionesModel;
import com.dragonregnan.sistemasdinamicos.model.solicitudesModel;

import java.util.ArrayList;

/**
 * Created by laura on 29/02/2016.
 */
public class HistorialAdapter extends BaseAdapter {
    Context context;
    private ArrayList<comprasModel> compras;

    public HistorialAdapter (Context context, ArrayList<comprasModel> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.compras = data;
    }

    @Override
    public int getCount() {
        return compras.size();
    }

    @Override
    public Object getItem(int position) {
        return compras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        empresasDAO empDAO = new empresasDAO(context);
        cotizacionesDAO cotdao = new cotizacionesDAO(context);
        solicitudesDAO solDAO = new solicitudesDAO(context);

        int idSolicitud = cotdao.getIdSolicitud(compras.get(position).getIdCotizacion());
        int idempresaCompradora = solDAO.getIdEmpCompradora(idSolicitud);
        int idempresaVendedora = cotdao.getIdEmpresaVendedora(compras.get(position).getIdCotizacion());
        String empresaCompradora = empDAO.getNombreEmpresa(idempresaCompradora);
        String empresaVendedora = empDAO.getNombreEmpresa(idempresaVendedora);

        cotizacionesModel cotizacion = new cotizacionesModel();
        cotizacion = cotdao.getCotizacion(compras.get(position).getIdCotizacion());


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.row_adaptador, null);
        TextView textTitulo = (TextView) vi.findViewById(R.id.txtvw_titulo);
        TextView textDetalle = (TextView) vi.findViewById(R.id.txtvw_detalle);


        String titulo = "Empresa Solicitante: "+ empresaCompradora + " Empresa Vendedrora: "+ empresaVendedora;
        String detalle = "Cantidad : "+ cotizacion.getCantOfrecida()+"/ Fecha de entrega: "+cotizacion.getFecEntrega();

        textTitulo.setText(titulo);
        textDetalle.setText(detalle);


        return vi;
    }
}
