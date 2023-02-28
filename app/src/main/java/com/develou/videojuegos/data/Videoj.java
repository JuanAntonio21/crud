package com.develou.videojuegos.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.develou.videojuegos.data.VideojContract.*;

import java.util.UUID;


public class Videoj {
    private String id;
    private String nombre;
    private String genero;
    private String precio;
    private String des;
    private String avatarUri;

    public Videoj(String nom,
                  String gen, String prec,
                  String desc, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nom;
        this.genero = gen;
        this.precio = prec;
        this.des = desc;
        this.avatarUri = avatarUri;
    }

    public Videoj(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(VideojEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(VideojEntry.NOMBRE));
        genero = cursor.getString(cursor.getColumnIndex(VideojEntry.GENERO));
        precio = cursor.getString(cursor.getColumnIndex(VideojEntry.PRECIO));
        des = cursor.getString(cursor.getColumnIndex(VideojEntry.DESC));
        avatarUri = cursor.getString(cursor.getColumnIndex(VideojEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VideojContract.VideojEntry.ID, id);
        values.put(VideojEntry.NOMBRE, nombre);
        values.put(VideojEntry.GENERO, genero);
        values.put(VideojEntry.PRECIO, precio);
        values.put(VideojEntry.DESC, des);
        values.put(VideojEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGen() {
        return genero;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDes() {
        return des;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

}
