package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Altas extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText clave, nombre, promedio;
    Spinner turnito, grupos;
    RadioGroup carrera;
    RadioButton progra, sd, meca, mqsa;
    Button regresar, añadir;
    ArrayAdapter adaptadito;
    Alistito alistito; // Instancia de la clase Alistito.
    ArrayAdapter<String> aadeptos;

    String[] papulinces = {"Selecciona", "Matutino", "Vespertino"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        clave = findViewById(R.id.Clave);
        nombre = findViewById(R.id.Nombre);
        promedio = findViewById(R.id.Promedio);
        turnito = findViewById(R.id.Turno);
        carrera = findViewById(R.id.Carreras);
        progra = findViewById(R.id.Progra);
        sd = findViewById(R.id.Sistemas);
        meca = findViewById(R.id.Meca);
        mqsa = findViewById(R.id.MQSA);
        regresar = findViewById(R.id.btnRegresarMenu);
        añadir = findViewById(R.id.btnAgregarProducto);
        grupos = findViewById(R.id.Grupo); // Actualiza la referencia al segundo Spinner.
        adaptadito = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, papulinces);
        turnito.setAdapter(adaptadito);

        alistito = new Alistito();

        regresar.setOnClickListener(this);
        añadir.setOnClickListener(this);
        turnito.setOnItemSelectedListener(this);
        grupos.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String cadenita = button.getText().toString();
        if (cadenita.equals("Agregar")) {
            Base admin = new Base(this, "administracion", null, 1);
            SQLiteDatabase basedatos = admin.getWritableDatabase();
            String cod = clave.getText().toString();
            String nom = nombre.getText().toString();
            String pro = promedio.getText().toString();
            String turnitoseleccionado = turnito.getSelectedItem().toString();
            int radioButtonId = carrera.getCheckedRadioButtonId();

            // Aquí se obtiene el RadioButton seleccionado utilizando su ID
            RadioButton radioButtonSeleccionado = findViewById(radioButtonId);
            String carreraSeleccionada = radioButtonSeleccionado.getText().toString();

            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("nombre", nom);

            // Cambia el nombre de la columna "carrera" en la base de datos para que coincida con el XML
            registro.put("carrera", carreraSeleccionada);

            // Añade la columna "grupo" en la base de datos y obtiene el valor del Spinner Grupos
            String grupoSeleccionado = grupos.getSelectedItem().toString();
            registro.put("grupo", grupoSeleccionado);

            registro.put("promedio", pro);
            registro.put("turno", turnitoseleccionado);

            basedatos.insert("articulos", null, registro);
            basedatos.close();
            Toast.makeText(this, "Dado de alta", Toast.LENGTH_SHORT).show();
            limpieza();
        } else {
            if (cadenita.equals("Regresar")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Pipipi", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void limpieza() {
        clave.setText("");
        promedio.setText("");
        nombre.setText("");
        turnito.setSelection(0); // Cambiado de "clearCheck()" a "setSelection(0)" para el Spinner
        grupos.setSelection(0); // Agregado para limpiar el Spinner Grupos
        carrera.clearCheck(); // Limpia la selección de RadioButtons
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.Turno) {
            String turnoSeleccionado = turnito.getSelectedItem().toString();
            alistito.agregar(turnoSeleccionado);
            ArrayAdapter<String> gruposAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alistito.regresar());
            grupos.setAdapter(gruposAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

