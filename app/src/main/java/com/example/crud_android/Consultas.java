package com.example.crud_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Consultas extends AppCompatActivity {
    EditText cod;
    Button cons, reg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        // Inicializar las vistas
        cod = findViewById(R.id.etClaveConsulta);
        cons = findViewById(R.id.btnConsultar);
        reg = findViewById(R.id.btnRegresar);

        // Configurar el evento de clic del botón Consultar
        cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarProducto();
            }
        });

        // Configurar el evento de clic del botón Regresar
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra la actividad actual y regresa a la anterior en la pila
                finish();
            }
        });
    }

    private void consultarProducto() {
        Base admin = new Base(this, "administracion", null, 1);
        SQLiteDatabase basededatos = admin.getReadableDatabase();
        String codigo = cod.getText().toString();

        Cursor fila = basededatos.rawQuery("SELECT nombre, carrera, promedio, turno, grupo FROM articulos WHERE codigo = ?", new String[]{codigo});
        if (fila.moveToFirst()) {
            String nombre = fila.getString(0);
            String carrera = fila.getString(1);
            double promedio = fila.getDouble(2);
            String turno = fila.getString(3);
            String grupo = fila.getString(4);

            mostrarAlertDialog("Detalles del Producto", "Nombre: " + nombre + "\nCarrera: " + carrera + "\nPromedio: " + promedio + "\nTurno: " + turno + "\nGrupo: " + grupo);
        } else {
            mostrarAlertDialog("Resultado", "No se encontraron datos para el código: " + codigo);
        }
        fila.close();
        basededatos.close();
    }

    private void mostrarAlertDialog(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
