package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
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

public class Cambios extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText etClave, etNombre, etPromedio;
    Button btnBuscar, btnRegresar;
    Spinner turnito, grupos;
    RadioGroup carrera;
    RadioButton progra, sd, meca, mqsa;
    ArrayAdapter<String> adaptadorTurno, adaptadorGrupo;
    Alistito alistito;
    String[] turnos = {"Selecciona", "Matutino", "Vespertino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        etClave = findViewById(R.id.Clave);
        etNombre = findViewById(R.id.Nombre);
        etPromedio = findViewById(R.id.Promedio);
        btnBuscar = findViewById(R.id.btnConsultar);
        btnRegresar = findViewById(R.id.btnRegresarMenu);
        carrera = findViewById(R.id.Carreras);
        turnito = findViewById(R.id.Turno);
        grupos = findViewById(R.id.Grupo);
        progra = findViewById(R.id.Progra);
        sd = findViewById(R.id.Sistemas);
        meca = findViewById(R.id.Meca);
        mqsa = findViewById(R.id.MQSA);

        adaptadorTurno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, turnos);
        turnito.setAdapter(adaptadorTurno);
        alistito = new Alistito();

        btnBuscar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);

        turnito.setOnItemSelectedListener(this);

        deshabilitarCampos();
    }

    private void deshabilitarCampos() {
        etNombre.setEnabled(false);
        etPromedio.setEnabled(false);
        carrera.setEnabled(false);
        turnito.setEnabled(false);
        grupos.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String cadenita = ((Button)v).getText().toString();
        if(cadenita.equals("Buscar")){
            buscarProducto();
        } else if(cadenita.equals("Cambiar")){
            modificarProducto();
        } else{
            finish();
        }
    }

    private void buscarProducto() {
        Base admin = new Base(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        String clave = etClave.getText().toString();
        Cursor fila = db.rawQuery("select nombre, promedio, carrera, turno, grupo from articulos where codigo = ?", new String[]{clave});
        if (fila.moveToFirst()) {
            etNombre.setText(fila.getString(0));
            etPromedio.setText(fila.getString(1));

            String turno = fila.getString(3);
            int posTurno = adaptadorTurno.getPosition(turno);
            turnito.setSelection(posTurno);
            actualizarSpinnerGrupos(turno);

            String grupo = fila.getString(4);
            int posGrupo = adaptadorGrupo.getPosition(grupo);
            grupos.setSelection(posGrupo);

            seleccionarCarrera(fila.getString(2));

            habilitarCampos();
            btnBuscar.setText("Cambiar");
        } else {
            Toast.makeText(this, "No se encontró el registro", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        db.close();
    }

    private void seleccionarCarrera(String carreraSeleccionada) {
        if ("Programacion".equals(carreraSeleccionada)) {
            carrera.check(R.id.Progra);
        } else if ("Sistemas_Digitales".equals(carreraSeleccionada)) {
            carrera.check(R.id.Sistemas);
        } else if ("Mecatrónica".equals(carreraSeleccionada)) {
            carrera.check(R.id.Meca);
        } else if ("Máquinas".equals(carreraSeleccionada)) {
            carrera.check(R.id.MQSA);
        }
    }

    private void modificarProducto() {
        Base admin = new Base(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String clave = etClave.getText().toString();
        ContentValues valores = new ContentValues();
        valores.put("nombre", etNombre.getText().toString());
        valores.put("promedio", etPromedio.getText().toString());
        valores.put("turno", turnito.getSelectedItem().toString());
        valores.put("grupo", grupos.getSelectedItem().toString());

        int idCarrera = carrera.getCheckedRadioButtonId();
        RadioButton rbCarrera = findViewById(idCarrera);
        valores.put("carrera", rbCarrera.getText().toString());

        int cantidad = db.update("articulos", valores, "codigo = ?", new String[]{clave});
        if (cantidad == 1) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
        }
        db.close();

        deshabilitarCampos();
        limpiarCampos();
        btnBuscar.setText("Buscar");
    }

    private void habilitarCampos() {
        etNombre.setEnabled(true);
        etPromedio.setEnabled(true);
        carrera.setEnabled(true);
        turnito.setEnabled(true);
        grupos.setEnabled(true);
    }

    private void limpiarCampos() {
        etClave.setText("");
        etNombre.setText("");
        etPromedio.setText("");
        carrera.clearCheck();
        turnito.setSelection(0);
        grupos.setSelection(0);
    }

    private void actualizarSpinnerGrupos(String turno) {
        alistito.agregar(turno);
        adaptadorGrupo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alistito.regresar());
        grupos.setAdapter(adaptadorGrupo);
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
