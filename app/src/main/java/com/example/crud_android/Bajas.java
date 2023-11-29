package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bajas extends AppCompatActivity implements View.OnClickListener {
    EditText etClaveProductoBaja;
    TextView tvProductosCanasta;
    Button btnAgregarE, btnEliminar, btnRegresar;
    Bajitas bajitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);

        etClaveProductoBaja = findViewById(R.id.etClaveProductoBaja);
        tvProductosCanasta = findViewById(R.id.tvProductosCanasta);
        btnAgregarE = findViewById(R.id.btnAgregarE);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);

        bajitas = new Bajitas();

        btnAgregarE.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAgregarE) {
            String clave = etClaveProductoBaja.getText().toString();
            bajitas.agregarCodigo(clave);
            actualizarListaEnUI();
            etClaveProductoBaja.setText(""); // Limpiar el EditText después de agregar
        } else if (v.getId() == R.id.btnEliminar) {
            eliminarAlumnos();
        } else if (v.getId() == R.id.btnRegresar) {
            finish(); // Cierra esta actividad y regresa a la anterior en la pila
        }
    }

    private void actualizarListaEnUI() {
        StringBuilder listaAlumnos = new StringBuilder();
        for (String codigo : bajitas.obtenerCodigos()) {
            listaAlumnos.append(codigo).append("\n");
        }
        tvProductosCanasta.setText(listaAlumnos.toString());
    }

    private void eliminarAlumnos() {
        Base admin = new Base(this, "administracion", null, 1);
        SQLiteDatabase basedatos = admin.getWritableDatabase();

        int totalEliminados = 0;
        for (String codigo : bajitas.obtenerCodigos()) {
            int filasEliminadas = basedatos.delete("articulos", "codigo = ?", new String[]{codigo});
            if (filasEliminadas > 0) {
                totalEliminados += filasEliminadas;
            }
        }

        basedatos.close();
        bajitas.limpiarCodigos();
        actualizarListaEnUI();

        if (totalEliminados > 0) {
            Toast.makeText(this, "Registros eliminados: " + totalEliminados, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontraron registros para eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
