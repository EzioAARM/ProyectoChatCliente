package com.ed2.aleja.utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilidades {

    public static final String UrlServer = "http://ec2-13-59-143-40.us-east-2.compute.amazonaws.com";
    public static final String Puerto = "3000";
    public static final String PuertoSocket = "3001";
    public static Retrofit RetrofitClient = new Retrofit
            .Builder()
            .baseUrl(Utilidades.UrlServer + ":" + Utilidades.Puerto + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static boolean verificarConexion(Context contexto) {
        ConnectivityManager manager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager == null ? null : manager.getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isConnected();
    }

    public static String CifrarSHA256(String texto) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void escribirToken(String tokenNuevo, Context contexto) throws IOException {
        OutputStreamWriter fout=
            new OutputStreamWriter(
                contexto.openFileOutput("auth.txt", Context.MODE_PRIVATE));
        fout.write(tokenNuevo);
        fout.close();
    }

    public static void escribirUsername(String username, Context contexto) throws IOException {
        OutputStreamWriter fout=
            new OutputStreamWriter(
                contexto.openFileOutput("user.txt", Context.MODE_PRIVATE));
        fout.write(username);
        fout.close();
    }

    public static String retornarToken(Context contexto) throws IOException {
        BufferedReader fin =
            new BufferedReader(
                new InputStreamReader(
                    contexto.openFileInput("auth.txt")));
        String texto = fin.readLine();
        fin.close();
        if (texto == null)
            texto = "";
        else
            texto = "Bearer " + texto;
        return texto;
    }

    public static String retornarUsername(Context contexto) throws IOException {
        BufferedReader fin =
            new BufferedReader(
                new InputStreamReader(
                    contexto.openFileInput("user.txt")));
        String texto = fin.readLine();
        fin.close();
        if (texto == null)
            texto = "";
        return texto;
    }

}
