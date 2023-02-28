package com.develou.videojuegos.data;

import android.provider.BaseColumns;


public class VideojContract {

    public static abstract class VideojEntry implements BaseColumns{
        public static final String TABLE_NAME ="viideojuegos";
        public static final String TABLE_NAME_usu ="usuarios";
        public static final String ID_usu = "id";
        public static final String NOMBRE_usu = "nombre";
        public static final String CONTRASEÑA = "contra";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String GENERO = "genero";
        public static final String PRECIO = "precio";
        public static final String AVATAR_URI = "avatarUri";
        public static final String DESC = "descripcion";
    }
}
