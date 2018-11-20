package com.ed2.aleja.utilidades;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class JwtUtility {

    public void escribirToken(String tokenNuevo, Context contexto) {
        try {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            contexto.openFileOutput("auth.txt", Context.MODE_PRIVATE));

            fout.write(tokenNuevo);
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String retornarToken(Context contexto) {
        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    contexto.openFileInput("auth.txt")));

            String texto = fin.readLine();
            fin.close();
            if (texto == null)
                texto = "";
            return texto;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
