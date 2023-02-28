package com.develou.videojuegos.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static com.develou.videojuegos.data.VideojContract.VideojEntry;

import androidx.fragment.app.Fragment;

/**
 * Manejador de la base de datos
 */
public class VideojDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "videojuegos.db";

    public VideojDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + VideojEntry.TABLE_NAME + " ("
                + VideojEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VideojEntry.ID + " TEXT NOT NULL,"
                + VideojEntry.NOMBRE + " TEXT NOT NULL,"
                + VideojEntry.GENERO + " TEXT NOT NULL,"
                + VideojEntry.PRECIO + " TEXT NOT NULL,"
                + VideojEntry.DESC + " TEXT NOT NULL,"
               + VideojEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + VideojEntry.ID + "))");

        db.execSQL("CREATE TABLE "+ VideojEntry.TABLE_NAME_usu+" ("
                + VideojEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VideojEntry.ID_usu + " TEXT NOT NULL,"
                + VideojEntry.NOMBRE_usu + " TEXT NOT NULL,"
                + VideojEntry.CONTRASEÑA+"TEXT NOT NULL,"
                + "UNIQUE (" + VideojEntry.ID_usu + "))");


        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockVideoj(sqLiteDatabase, new Videoj("GTA V", "Accion-Aventura, Mundo abierto, Shoter",
                "59.99", "Grand Theft Auto V es un videojuego de acción-aventura de mundo abierto desarrollado por la compañía británica Rockstar North" +
                "y distribuido por Rockstar Games. Fue lanzado el 17 de septiembre de 2013 para las consolas PlayStation 3 y Xbox 360.",
                "gta.jpg"));
        mockVideoj(sqLiteDatabase, new Videoj("Red Dead Redemption 2", "Acción-Aventura, Antiguo Oeste, Shoter",
                "49.99", "Una historia épica sobre la vida en el despiadado corazón de América. Su vasto y evocador mundo sentará, a su vez, los cimientos para una experiencia multijugador online totalmente nueva.",
                "RD2.jpg"));
        mockVideoj(sqLiteDatabase, new Videoj("The Last Of Us", "Terror, Acción-Aventura",
                "30.00", "En septiembre de 2013 se desata una pandemia en Estados Unidos ocasionada por una cepa del hongo Cordyceps, que al infectar a los humanos los convierte en criaturas caníbales, y que se transmite a través de una simple mordedura.",
                "TLOU.jpg"));
        mockVideoj(sqLiteDatabase, new Videoj("Torrente 3: El Protector", "Accion-Aventura, Shoter, Mundo abierto",
                "99.99", "En Torrente 3 tendremos que resolver todo tipo de situaciones conflictivas en las calles de Madrid con el único fin de aumentar nuestro nivel de Torrentismo, dividido en cuatro escalafones, que será la experiencia general adquirida por el protagonista a lo largo de la aventura.",
                "Torrente3.jpg"));
        mockVideoj(sqLiteDatabase, new Videoj("Days Gone", "Terror, Acción-Aventura",
                "39.99", " es una aventura de acción sobre supervivientes y aquello que los hace humanos: la desesperación, la pérdida, la locura, la traición, la amistad, la hermandad, el arrepentimiento, el amor y la esperanza.",
                "DG.jpg"));
        //mockUsuario(sqLiteDatabase, new Usuario("a","a"));

    }

    public long mockVideoj(SQLiteDatabase db, Videoj videoj) {
        return db.insert(
                VideojContract.VideojEntry.TABLE_NAME,
                null,
                videoj.toContentValues());
    }

    public long mockUsuario(SQLiteDatabase db, Usuario usu) {
        return db.insert(
                VideojEntry.TABLE_NAME_usu,
                null,
                usu.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveVid(Videoj vid) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                VideojEntry.TABLE_NAME,
                null,
                vid.toContentValues());

    }

    public long saveUsu(Usuario usu) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                VideojEntry.TABLE_NAME_usu,
                null,
                usu.toContentValues());

    }

    public Cursor getAllVid() {
        return getReadableDatabase()
                .query(
                        VideojEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAllUsu() {
        return getReadableDatabase()
                .query(
                        VideojEntry.TABLE_NAME_usu,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getVideojById(String videojuegoId) {
        Cursor c = getReadableDatabase().query(
                VideojEntry.TABLE_NAME,
                null,
                VideojEntry.ID + " LIKE ?",
                new String[]{videojuegoId},
                null,
                null,
                null);
        return c;
    }

    public int deleteVid(String vidjId)  {
        return getWritableDatabase().delete(
                VideojEntry.TABLE_NAME,
                VideojEntry.ID + " LIKE ?",
                new String[]{vidjId});

    }

    public int updateVideoj(Videoj vid, String vidId) {
        return getWritableDatabase().update(
                VideojEntry.TABLE_NAME,
                vid.toContentValues(),
                VideojEntry.ID + " LIKE ?",
                new String[]{vidId}
        );

    }
}
