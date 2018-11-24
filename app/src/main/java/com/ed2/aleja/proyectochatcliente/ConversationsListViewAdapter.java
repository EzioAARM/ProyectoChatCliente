package com.ed2.aleja.proyectochatcliente;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;
import java.util.ArrayList;

public class ConversationsListViewAdapter extends BaseAdapter {

    private ArrayList<ConversationListViewItem> ItemsList;
    private Context Contexto;

    public ConversationsListViewAdapter(ArrayList<ConversationListViewItem> itemsList, Context contexto) {
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
        convertView = LayoutInflater.from(Contexto).inflate(R.layout.conversation_list_item, null);
        TextView emisorMensaje = (TextView) convertView.findViewById(R.id.emisor_mensaje_conversacion);
        TextView contenidoMensaje = (TextView) convertView.findViewById(R.id.contenido_mensaje_conversacion);
        TextView idMensaje = (TextView) convertView.findViewById(R.id.id_conversacion_conversacion);
        TextView mensajesNuevos = (TextView) convertView.findViewById(R.id.cantidad_mensajes_nuevos);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.profile_image_conversation_list);
        RelativeLayout contenedor = (RelativeLayout) convertView.findViewById(R.id.contenedor_mensajes_nuevos);

        ConversationListViewItem item = (ConversationListViewItem) getItem(position);

        if (item.getNuevos() == 0) {
            contenedor.setVisibility(View.INVISIBLE);
        } else {
            try {
                if (item.getSender().equals(Utilidades.retornarUsername(Contexto))) {
                    contenedor.setVisibility(View.INVISIBLE);
                } else {
                    contenedor.setVisibility(View.VISIBLE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (item.getImagen().equals("")) {
            imagen.setImageDrawable(convertView.getResources().getDrawable(R.drawable.ic_profile_picture));
        } else {

        }

        mensajesNuevos.setText(String.valueOf(item.getNuevos()));
        emisorMensaje.setText(item.getUsuarioEmisor());
        contenidoMensaje.setText(item.getContenidoUltimoMensaje());
        idMensaje.setText(item.getIdConversacion());

        return convertView;
    }
}
