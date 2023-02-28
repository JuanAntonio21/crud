package com.develou.videojuegos.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.develou.videojuegos.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void start(View view) {
        Intent intento= new Intent(this,IniciSes.class);
        intento.putExtra("correo","");
        intento.putExtra("contraseña","");
        startActivity(intento);
    }

    public void register(View view) {
        Intent intento= new Intent(this,Registro.class);
        startActivity(intento);
    }

}