package com.ed2.aleja.proyectochatcliente;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MensajeListViewItem {

    private String username;

    private String mensaje;

    private boolean TieneArchivo;

    private String RutaArchivo;

    public MensajeListViewItem(String username, String mensaje, boolean tieneArchivo, String rutaArchivo) {
        this.username = username;
        this.mensaje = mensaje;
        this.TieneArchivo = tieneArchivo;
        this.RutaArchivo = rutaArchivo;
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

    public boolean isTieneArchivo() {
        return TieneArchivo;
    }

    public void setTieneArchivo(boolean tieneArchivo) {
        TieneArchivo = tieneArchivo;
    }

    public String getRutaArchivo() {
        return RutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        RutaArchivo = rutaArchivo;
    }
}
