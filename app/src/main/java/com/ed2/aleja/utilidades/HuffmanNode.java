package com.ed2.aleja.utilidades;

public class HuffmanNode {

    public String codeWord;
    public int prob;
    public char aChar;
    public HuffmanNode left;
    public HuffmanNode right;
    public int valorComparacion = 0;

    public HuffmanNode(int freq, char caracter){
        prob = freq;
        aChar = caracter;
        valorComparacion = (int) aChar;
    }

    public boolean esHoja(){
        if (left == null && right == null){
            return  true;
        }
        return false;
    }
}
