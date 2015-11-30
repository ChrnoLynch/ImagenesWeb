package com.example.lprub.imagenesweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lprub on 28/11/2015.
 */
public class Adaptador extends ArrayAdapter{
    private Context contexto;
    private int recurso;
    private ArrayList ruta;


    public Adaptador(Context contexto, int recurso, ArrayList ruta) {
        super(contexto, recurso, ruta);
        this.contexto = contexto;
        this.recurso = recurso;
        this.ruta = ruta;
    }

    static class SalvadorReferencia {
        public TextView tvRuta;
    }

    @Override
    public View getView(final int posicion, View vista, ViewGroup padre) {
        LayoutInflater i = (LayoutInflater) contexto.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        SalvadorReferencia sr = new SalvadorReferencia();
        if (vista == null) {
            vista = i.inflate(recurso, null);
            sr.tvRuta = (TextView) vista.findViewById(R.id.tvRuta);
            vista.setTag(sr);
        } else {
            sr = (SalvadorReferencia) vista.getTag();
        }
        sr.tvRuta.setText(ruta.get(posicion).toString());
        return vista;
    }
}
