package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

public class Mensaje {

    @SerializedName("_id")
    public String _id;

    @SerializedName("mensaje")
    public String Mensaje;

    @SerializedName("emisor")
    public String Emisor;

    @SerializedName("receptor")
    public String Receptor;

    @SerializedName("tieneArchivo")
    public boolean tieneArchivo;

    @SerializedName("ubicacionArchivo")
    public String rutaArchivoServer;

    @SerializedName("leido")
    public boolean leido;

    @SerializedName("hayGrupo")
    public boolean hayGrupo;

    @SerializedName("fechaEnviado")
    public String fechaEnviado;

    @SerializedName("horaEnviado")
    public String horaEnviado;

    @SerializedName("token")
    public String token;

    public Mensaje(String mensaje, String emisor, String receptor, boolean tieneArchivo, String rutaArchivoServer, boolean leido, boolean hayGrupo, String fechaEnviado, String horaEnviado) {
        Mensaje = mensaje;
        Emisor = emisor;
        Receptor = receptor;
        this.tieneArchivo = tieneArchivo;
        this.rutaArchivoServer = rutaArchivoServer;
        this.leido = leido;
        this.hayGrupo = hayGrupo;
        this.fechaEnviado = fechaEnviado;
        this.horaEnviado = horaEnviado;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String emisor) {
        Emisor = emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }

    public boolean isTieneArchivo() {
        return tieneArchivo;
    }

    public void setTieneArchivo(boolean tieneArchivo) {
        this.tieneArchivo = tieneArchivo;
    }

    public String getRutaArchivoServer() {
        return rutaArchivoServer;
    }

    public void setRutaArchivoServer(String rutaArchivoServer) {
        this.rutaArchivoServer = rutaArchivoServer;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean isHayGrupo() {
        return hayGrupo;
    }

    public void setHayGrupo(boolean hayGrupo) {
        this.hayGrupo = hayGrupo;
    }

    public String getFechaEnviado() {
        return fechaEnviado;
    }

    public void setFechaEnviado(String fechaEnviado) {
        this.fechaEnviado = fechaEnviado;
    }

    public String getHoraEnviado() {
        return horaEnviado;
    }

    public void setHoraEnviado(String horaEnviado) {
        this.horaEnviado = horaEnviado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
