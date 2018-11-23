package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {

    @SerializedName("_id")
    public String id;

    @SerializedName("token")
    String Token;

    @SerializedName("username")
    public String Username;

    @SerializedName("password")
    public String Password;

    @SerializedName("nombre")
    public String Nombre;

    @SerializedName("apellido")
    public String Apellido;

    @SerializedName("telefono")
    public String Telefono;

    @SerializedName("fechaNacimiento")
    public String FechaNacimiento;

    @SerializedName("correo")
    public String Correo;

    @SerializedName("status")
    public boolean Status;

    @SerializedName("imagen")
    public String Imagen;

    public Usuario() {

    }

    public Usuario(String username, String password, String nombre, String apellido, String telefono, String fechaNacimiento, String correo, boolean status, String imagen) {
        Username = username;
        Password = password;
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        FechaNacimiento = fechaNacimiento;
        Correo = correo;
        Status = status;
        Imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public boolean isStatus() {
        return Status;
    }

    public String toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", Username);
        jsonObject.put("password", Password);
        jsonObject.put("nombre", Nombre);
        jsonObject.put("apellido", Apellido);
        jsonObject.put("fechaNacimiento", FechaNacimiento);
        jsonObject.put("correo", Correo);
        jsonObject.put("status", Status);
        jsonObject.put("imagen", Imagen);
        return jsonObject.toString();
    }
}