package com.develou.videojuegos.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.develou.videojuegos.R;
import com.develou.videojuegos.videoj.VideojActivity;
import com.develou.videojuegos.videoj.VideojFragment;

public class IniciSes extends AppCompatActivity {
    String correoC,passwdC;
    EditText correo,contraseña;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicioses);
        Bundle datos = this.getIntent().getExtras();
        correoC= datos.getString("correo");
        passwdC=datos.getString("contraseña");
        correo=findViewById(R.id.edtIniCon);
        contraseña=findViewById(R.id.editTextTextPassword);
    }

    public void iniciar(View view) {
        if(correo.getText().toString().equals(correoC) && contraseña.getText().toString().equals(passwdC)){
            Toast.makeText(this, "Credenciales correctas", Toast.LENGTH_SHORT).show();
            Intent intento= new Intent(this, VideojActivity.class);
            startActivity(intento);
        }else{
            Toast.makeText(this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}
