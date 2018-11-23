package com.ed2.aleja.proyectochatcliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListViewadapter extends BaseAdapter {

    private ArrayList<ContactListViewItem> ItemsList;
    private Context Contexto;

    public ContactListViewadapter(ArrayList<ContactListViewItem> itemsList, Context contexto) {
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
        convertView = LayoutInflater.from(Contexto).inflate(R.layout.contact_list_item, null);
        ImageView fotoPerfil = (ImageView) convertView.findViewById(R.id.imagen_perfil_contact_list);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre_contact_list);
        TextView username = (TextView) convertView.findViewById(R.id.username_contact_list);

        ContactListViewItem itemActual = (ContactListViewItem) getItem(position);

        if (itemActual.Imagen == "") {
            fotoPerfil.setImageDrawable(convertView.getResources().getDrawable(R.drawable.ic_profile_picture));
        }
        nombre.setText(itemActual.Nombre);
        username.setText(itemActual.Username);

        return convertView;
    }
}
