package com.ed2.aleja.proyectochatcliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;
import java.util.ArrayList;

public class MensajeListViewAdapter extends BaseAdapter {

    private ArrayList<MensajeListViewItem> ItemsList;
    private Context Contexto;

    public MensajeListViewAdapter(ArrayList<MensajeListViewItem> itemsList, Context contexto) {
        ItemsList = itemsList;
        Contexto = contexto;
    }

    @Override
    public int getCount() {
        return ItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return ItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (ItemsList.get(position).getUsername().equals(Utilidades.retornarUsername(Contexto))){
                convertView = LayoutInflater.from(Contexto).inflate(R.layout.item_mensaje_enviado, null);
            } else {
                convertView = LayoutInflater.from(Contexto).inflate(R.layout.item_mensaje_recibido, null);
            }
            TextView TextViewMensaje = convertView.findViewById(R.id.mensaje_chat);
            TextView TextViewUsername = convertView.findViewById(R.id.username_chat);

            TextViewMensaje.setText(ItemsList.get(position).getMensaje());
            TextViewUsername.setText(ItemsList.get(position).getUsername());
            return convertView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
