package com.ed2.aleja.proyectochatcliente;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MensajeListViewItem {

    private String username;

    private String mensaje;

    public MensajeListViewItem(String username, String mensaje) {
        this.username = username;
        this.mensaje = mensaje;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
