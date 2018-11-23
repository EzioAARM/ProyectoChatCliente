package com.ed2.aleja.proyectochatcliente;

public class ContactListViewItem {

    public String Username;
    public String Nombre;
    public String Imagen;

    public ContactListViewItem(String username, String nombre, String imagen) {
        Username = username;
        Nombre = nombre;
        Imagen = imagen;
    }


    public String getUsername() {
        return Username;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
