package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bot1, bot2, bot3, bot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bot1 = findViewById(R.id.altas);
        bot2 = findViewById(R.id.bajas);
        bot3 = findViewById(R.id.cambios);
        bot4 = findViewById(R.id.consultas);

        bot1.setOnClickListener(this);
        bot2.setOnClickListener(this);
        bot3.setOnClickListener(this);
        bot4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cadenita  = ((Button)view).getText().toString();
        if(cadenita.equals("Altas")){
            Intent intent = new Intent(this, Altas.class);
            startActivity(intent);
        }else if(cadenita.equals("Bajas")){
            Intent intent = new Intent(this, Bajas.class);
            startActivity(intent);
        }else if(cadenita.equals("Cambios")){
            Intent intent = new Intent(this, Cambios.class);
            startActivity(intent);
        }else if(cadenita.equals("Consultas")){
            Intent intent = new Intent(this, Consultas.class);
            startActivity(intent);
        }
    }
}