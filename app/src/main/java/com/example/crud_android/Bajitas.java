package com.example.crud_android;

import java.util.ArrayList;

public class Bajitas {
    private ArrayList<String> codigosParaBaja;

    public Bajitas() {
        codigosParaBaja = new ArrayList<>();
    }

    public void agregarCodigo(String codigo) {
        codigosParaBaja.add(codigo);
    }

    public ArrayList<String> obtenerCodigos() {
        return codigosParaBaja;
    }

    public void limpiarCodigos() {
        codigosParaBaja.clear();
    }
}
