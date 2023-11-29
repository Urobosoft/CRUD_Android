package com.example.crud_android;

import java.util.ArrayList;

public class Alistito {
    ArrayList<String> Adatitos = new ArrayList<>();

    public void agregar(String turno) {
        Adatitos.clear(); // Limpia la lista antes de agregar nuevos elementos.
        if ("Matutino".equals(turno)) {
            for (int i = 1; i <= 5; ++i) {
                Adatitos.add("M" + i);
            }
        } else if ("Vespertino".equals(turno)) {
            for (int i = 1; i <= 5; ++i) {
                Adatitos.add("V" + i);
            }
        }
    }

    public ArrayList<String> regresar(){
        return Adatitos;
    }
}