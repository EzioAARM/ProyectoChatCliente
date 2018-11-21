package com.ed2.aleja.proyectochatcliente;

public class ConversationListViewItem {

    private String UsuarioEmisor;
    private String ContenidoUltimoMensaje;
    private int Nuevos;
    private String idConversacion;
    private String Imagen;

    public ConversationListViewItem(String _id, String _username, String _contenido, int _nuevos, String imagen) {
        UsuarioEmisor = _username;
        ContenidoUltimoMensaje = _contenido;
        Nuevos = _nuevos;
        idConversacion = _id;
        Imagen = imagen;
    }

    public String getImagen() {
        return Imagen;
    }

    public int getNuevos() {
        return Nuevos;
    }

    public String getContenidoUltimoMensaje() {
        return ContenidoUltimoMensaje;
    }

    public String getIdConversacion() {
        return idConversacion;
    }

    public String getUsuarioEmisor() {
        return UsuarioEmisor;
    }
}
