package com.ed2.aleja.proyectochatcliente;

public class ConversationListViewItem {

    private String UsuarioEmisor;
    private String ContenidoUltimoMensaje;
    private int Nuevos;
    private String idConversacion;
    private String Imagen;
    private String Sender;

    public ConversationListViewItem(String _id, String _username, String _contenido, int _nuevos, String imagen, String sender) {
        UsuarioEmisor = _username;
        ContenidoUltimoMensaje = _contenido;
        Nuevos = _nuevos;
        idConversacion = _id;
        Imagen = imagen;
        Sender = sender;
    }

    public String getSender() {
        return Sender;
    }

    public void setUsuarioEmisor(String usuarioEmisor) {
        UsuarioEmisor = usuarioEmisor;
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

    public void setContenidoUltimoMensaje(String contenidoUltimoMensaje) {
        ContenidoUltimoMensaje = contenidoUltimoMensaje;
    }

    public void setNuevos(int nuevos) {
        Nuevos = nuevos;
    }

    public void setIdConversacion(String idConversacion) {
        this.idConversacion = idConversacion;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
