package com.develou.videojuegos.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.develou.videojuegos.R;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
    EditText nombre,apellido,fecha,correo,tlf,contraseña,contraseñaComprobacion;
    String nom,ape,email,fechaD,telf,passwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        nombre=findViewById(R.id.edtNom);
        apellido=findViewById(R.id.edtApe);
        fecha=findViewById(R.id.edtDat);
        correo=findViewById(R.id.editTextTextEmailAddress);
        tlf=findViewById(R.id.edtTel);
        contraseña=findViewById(R.id.edtCon);
        contraseñaComprobacion=findViewById(R.id.edtCon2);

    }

    public void cancelar(View view){
        Intent intent = new Intent (view.getContext(), MainActivity.class);

        startActivity(intent);
    }

    public void guardar(View view) {
        nom=nombre.getText().toString();
        ape=apellido.getText().toString();
        fechaD=fecha.getText().toString();
        email=correo.getText().toString();
        telf=tlf.getText().toString();
        if(nom.length()==0||ape.length()==0||fechaD.length()==0||email.length()==0||telf.length()==0){
            Toast.makeText(this, "Rellene los campos porfavor", Toast.LENGTH_SHORT).show();
        }else{
            if(!nom.matches("[a-zA-Z]")){
                Toast.makeText(this, "El campo nombre solo puede contener letras", Toast.LENGTH_SHORT).show();
            }else{
                if(!ape.matches("[a-zA-Z]")){
                    Toast.makeText(this, "El campo apellidos solo puede contener letras", Toast.LENGTH_SHORT).show();
                }else{
                    Pattern pattern = Pattern
                            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                    Matcher mather = pattern.matcher(email);

                    if(!mather.find()){
                        Toast.makeText(this, "Introduzca un email correcto", Toast.LENGTH_SHORT).show();
                    }else{

                        if(tlf.length()<9 || tlf.length()>9){
                            Toast.makeText(this, "La longitud del numero de TLF es incorrecta", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(contraseña.getText().toString().equals(contraseñaComprobacion.getText().toString())){
                                passwd=contraseña.getText().toString();
                                Intent datos = new Intent (view.getContext(), IniciSes.class);

                                // Intent datos=new Intent(this,IniciSes.class);
                                datos.putExtra("correo",email);
                                datos.putExtra("contraseña",passwd);
                                startActivity(datos);
                            }else{
                                Toast.makeText(this, "Las contraseñas no coinciden :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }


        }



    }
}
