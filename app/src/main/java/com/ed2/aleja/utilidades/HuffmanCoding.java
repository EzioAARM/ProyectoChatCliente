package com.ed2.aleja.utilidades;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanCoding {
    // Generales
    ArrayList<HuffmanNode> ListaNodos = new ArrayList<>();
    ArrayList<HuffmanNode> ListaAux = new ArrayList<>();
    ArrayList<HuffmanNode> Hojas = new ArrayList<>();
    Map<Character, String> tabla = new TreeMap<>();
    boolean compresion = true;
    public double Factor;
    public double Razon;


    String TextoArchivo;
    int extras = 0;
    Context Contexto;
    public String NombreArchivoNuevo = "";
    public String NombreOriginalArchivo = "";
    public String outPut = "";
    public String ubicacionArchivo = "";

    public HuffmanCoding(String texto, Context contextoApp){
        Contexto = contextoApp;
        TextoArchivo = texto;
    }

    // Proceso de compresión
    public void Comprimir() throws Exception {
        compresion = true;
        getSimbolos();
        build();
        traverse(ListaNodos.get(0));
        generarCompresion();
        ListaNodos.clear(); //limpio la lista de nodos por di acaso
        Hojas.clear();
    }

    private void getSimbolos() throws Exception {
        char caracter = TextoArchivo.charAt(0);
        ListaNodos.add(new HuffmanNode(1, caracter));
        for (int i = 1; i < TextoArchivo.length(); i++) {
            caracter = TextoArchivo.charAt(i);
            for (int j = 0; j < ListaNodos.size(); j++) {
                if (ListaNodos.get(j).aChar == caracter) {
                    ListaNodos.get(j).prob++;
                    j = ListaNodos.size();
                } else if (j == (ListaNodos.size() - 1)) {
                    ListaNodos.add(new HuffmanNode(1, caracter));
                }
            }
        }
    }

    private void mySort(){
        HuffmanNode temp;
        for (int i = 0; i < ListaNodos.size() - 1; i++) {
            for (int j = 0; j < ListaNodos.size() - i - 1; j++) {
                if (ListaNodos.get(j).prob >= ListaNodos.get(j+1).prob) {
                    if (ListaNodos.get(j).prob == ListaNodos.get(j+1).prob) {
                        if (ListaNodos.get(j).valorComparacion > ListaNodos.get(j + 1).valorComparacion) {
                            temp = ListaNodos.get(j);
                            ListaNodos.remove(j);
                            ListaNodos.add(j+1, temp);
                        }
                    } else if (ListaNodos.get(j).prob > ListaNodos.get(j+1).prob) {
                        temp = ListaNodos.get(j);
                        ListaNodos.remove(j);
                        ListaNodos.add(j+1, temp);
                    }
                }
            }
        }
    }

    public  void copy(){
        char aTemp;
        int pTemp;
        HuffmanNode temp;
        for(int i = 0; i < ListaNodos.size(); i++){
            aTemp = ListaNodos.get(i).aChar;
            pTemp = ListaNodos.get(i).prob;
            temp = new HuffmanNode(pTemp, aTemp);
            ListaAux.add(temp);
        }
    }

    private  void build() throws Exception {
        mySort();
        copy();
        HuffmanNode temp;
        int freqT;
        while (ListaNodos.size() > 1) {
            freqT = ListaNodos.get(0).prob + ListaNodos.get(1).prob;
            temp = new HuffmanNode(freqT, 'む');
            temp.valorComparacion = ListaNodos.get(0).valorComparacion + ListaNodos.get(1).valorComparacion;
            temp.right = ListaNodos.get(0);
            temp.left = ListaNodos.get(1);
            ListaNodos.remove(0);
            ListaNodos.remove(0);
            ListaNodos.add(temp);
            mySort();
        }
    }

    private void traverse(HuffmanNode arbol) throws Exception {
        HuffmanNode temp = arbol;
        if ( temp == null){
            return;
        }
        traverse(temp.left, "0", "");
        traverse(temp.right, "1", "");
    }

    private void traverse(HuffmanNode arbol, String code, String codeWordParam){
        HuffmanNode temp = arbol;
        if ( temp == null){
            return;
        }
        traverse(temp.left, "0", codeWordParam + code);
        if (temp.esHoja()){
            temp.codeWord = codeWordParam + code;
            Hojas.add(temp);
            tabla.put(temp.aChar, temp.codeWord);
        }
        traverse(temp.right, "1", codeWordParam + code);
    }

    private void generarCompresion () throws Exception {
        String binario = "";
        for (int i = 0; i < TextoArchivo.length(); i++) {
            binario += tabla.get(TextoArchivo.charAt(i));
        }
        String cadenaEscribir = "";
        String caracterNuevo = "";
        String comodin = "";
        for (int i = 0; i < binario.length(); i++) {
            if (caracterNuevo.length() < 8){
                caracterNuevo += binario.charAt(i);
            } else if (caracterNuevo.length() == 8) {
                cadenaEscribir += (char) Integer.parseInt(caracterNuevo, 2);
                caracterNuevo = String.valueOf(binario.charAt(i));
            } if (caracterNuevo.length() < 8 && i == binario.length() - 1) {
                for (int j = 0; j < (8 - caracterNuevo.length()); j++) {
                    comodin += "0";
                }
                extras = comodin.length();
                caracterNuevo = caracterNuevo + comodin;
            }
        }
        double cantidadTemp = (String.valueOf(extras).length() + getNombreOriginalArchivo().length() + 2 + getTablaCaracteres().length() + cadenaEscribir.length());
        double originalTemp = TextoArchivo.length();
        Factor = originalTemp / cantidadTemp;
        String temp;
        if (String.valueOf(Factor).length() > 7){
            temp = String.valueOf(Factor).substring(0, 7);
        } else {
            temp = String.valueOf(Factor);
        }
        Factor = Double.parseDouble(temp);
        Razon = cantidadTemp / originalTemp;
        if (String.valueOf(Razon).length() > 7) {
            temp = String.valueOf(Razon).substring(0, 7);
        } else {
            temp = String.valueOf(Razon);
        }
        Razon = Double.parseDouble(temp);
        escribirArchivoCompreso(getNombreArchivoNuevo(), String.valueOf(extras) + getNombreOriginalArchivo() + "☺☺" + getTablaCaracteres() + cadenaEscribir);
    }

    private boolean escribirArchivoCompreso(String nombreArchivo, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            directorio = new File(Environment.getExternalStorageDirectory() + "/CompresionesEstructuras/");
        else
            directorio = new File(Contexto.getFilesDir() + "/CompresionesEstructuras/");
        boolean dirCre = true;
        if (!directorio.exists())
            dirCre = directorio.mkdirs();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        File archivoEscribir = new File(directorio.getAbsolutePath() + "/" + nombreArchivo + ".huff");
        if (!archivoEscribir.createNewFile())
            throw new Exception("No se pudo crear el archivo " + directorio.getAbsolutePath());
        FileOutputStream fileOutputStream = new FileOutputStream(archivoEscribir);
        fileOutputStream.write(contenido.getBytes());
        fileOutputStream.close();
        ubicacionArchivo = archivoEscribir.getAbsolutePath();
        return true;
    }

    // Descomprimir
    private boolean buscarCodeWord(String code) {
        String codeWordComparar = "";
        for (int i = 0; i < Hojas.size(); i++) {
            codeWordComparar = Hojas.get(i).codeWord;
            if (codeWordComparar.equals(code)){
                outPut += String.valueOf(Hojas.get(i).aChar);
                return true;
            }
        }
        return false;
    }

    public void Descomprimir() throws Exception {
        compresion = false;
        String cadenaBinario = "";
        String temp = ""; String cerosExtras = "";
        separarContenido();
        for (int i = 0; i < TextoArchivo.length(); i++) {
            temp = Integer.toString((int) TextoArchivo.charAt(i), 2);
            for (int j = 0; j < (8 - temp.length()); j++) {
                cerosExtras += "0";
            }
            cadenaBinario += cerosExtras + temp;
            cerosExtras = "";
        }
        String busquedaCode = "";
        for (int i = 0; i < cadenaBinario.length(); i++) {
            if (buscarCodeWord(busquedaCode)) {
                busquedaCode = "";
                i--;
            } else {

                busquedaCode += cadenaBinario.charAt(i);
            }
        }
        escribirArchivoDescompreso(NombreOriginalArchivo, outPut);
    }

    private void separarContenido() throws Exception {
        extras = Integer.parseInt(String.valueOf(TextoArchivo.charAt(0)));
        TextoArchivo = TextoArchivo.substring(1);
        for (int i = 0; i < TextoArchivo.length(); i++) {
            if (String.valueOf(TextoArchivo.charAt(i)).equals("☺") && String.valueOf(TextoArchivo.charAt(i + 1)).equals("☺")) {
                TextoArchivo = TextoArchivo.substring(i + 2);
                i = TextoArchivo.length();
            } else {
                NombreOriginalArchivo += TextoArchivo.charAt(i);
            }
        }
        String tablaCaracteres = "";
        for (int i = 0; i < TextoArchivo.length(); i++) {
            if (String.valueOf(TextoArchivo.charAt(i)).equals("☺") && String.valueOf(TextoArchivo.charAt(i + 1)).equals("☺")) {
                TextoArchivo = TextoArchivo.substring(i + 2);
                i = TextoArchivo.length();
            } else {
                tablaCaracteres += TextoArchivo.charAt(i);
            }
        }
        char caracter; int apariciones;
        String aparicionesTemp = "";
        int i = 0;
        while (tablaCaracteres.length() != 0) {
            caracter = tablaCaracteres.charAt(0);
            tablaCaracteres = tablaCaracteres.substring(1);
            for (int j = 0; j < 4; j++) {
                aparicionesTemp += tablaCaracteres.charAt(j);
            }
            apariciones = Integer.parseInt(aparicionesTemp);
            tablaCaracteres = tablaCaracteres.substring(4);
            aparicionesTemp = "";
            ListaNodos.add(new HuffmanNode(apariciones, caracter));
        }
        build();
        traverse(ListaNodos.get(0));
    }

    public void escribirArchivoDescompreso(String nombreArchivo, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            directorio = new File(Environment.getExternalStorageDirectory() + "/DescompresionesEstructuras/");
        else
            directorio = new File(Contexto.getFilesDir() + "/DescompresionesEstructuras/");
        boolean dirCre = true;
        if (!directorio.exists())
            dirCre = directorio.mkdirs();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        File archivoEscribir = new File(directorio.getAbsolutePath() + "/" + nombreArchivo);
        if (archivoEscribir.exists()) {
            throw new Exception("El archivo " + nombreArchivo + " ya existe");
        } else {
            if (!archivoEscribir.createNewFile())
                throw new Exception("No se pudo crear el archivo " + archivoEscribir.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(archivoEscribir);
            fileOutputStream.write(contenido.getBytes());
            fileOutputStream.close();
        }
    }

    // Extras
    public void setNombreOriginalArchivo(String nombre) {
        NombreOriginalArchivo = nombre;
    }

    public String getNombreOriginalArchivo() {
        return NombreOriginalArchivo;
    }

    public void setNombreArchivoNuevo (String nombre){
        NombreArchivoNuevo = nombre;
    }

    public String getNombreArchivoNuevo () {
        return NombreArchivoNuevo;
    }

    private String getTablaCaracteres () {
        //use ListaAux para la tabla de caracteres.
        String datos = "";
        String numero = "";
        String ceros = "";
        for (int i = 0; i < ListaAux.size(); i++) {
            if (String.valueOf(ListaAux.get(i).prob).length() < 4) {
                for (int j = 0; j < (4 - String.valueOf(ListaAux.get(i).prob).length()); j++){
                    ceros += "0";
                }
                numero = ceros + String.valueOf(ListaAux.get(i).prob);
                ceros = "";
            }
            datos += String.valueOf(ListaAux.get(i).aChar) + numero;
        }
        datos += "☺☺";
        return datos;
    }
}
