package com.develou.videojuegos.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Usuario {

    private String id;
    private String Nombre;
    private String Contraseña;

    public Usuario(String nom,
                  String cont) {
        this.id = UUID.randomUUID().toString();
        this.Nombre = nom;
        this.Contraseña = cont;
    }

    @SuppressLint("Range")
    public Usuario(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(VideojContract.VideojEntry.ID_usu));
        Nombre = cursor.getString(cursor.getColumnIndex(VideojContract.VideojEntry.NOMBRE_usu));
        Contraseña = cursor.getString(cursor.getColumnIndex(VideojContract.VideojEntry.CONTRASEÑA));

    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VideojContract.VideojEntry.ID_usu, id);
        values.put(VideojContract.VideojEntry.NOMBRE_usu, Nombre);
        values.put(VideojContract.VideojEntry.CONTRASEÑA, Contraseña);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getContraseña() {
        return Contraseña;
    }

}
