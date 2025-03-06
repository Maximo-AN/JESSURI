package com.proyecto.jessuri.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbPrincipal extends SQLiteOpenHelper {
    private static final int VERSION_DB = 4;
    private static final String NOMBRE_DB = "jessure.db";
    public static final String TABLA_USUARIOS = "t_usuarios";
    public static final String TABLA_PRODUCTOS = "t_productos";
    public static final String TABLA_TICKETS = "t_tickets";
    public static String CARPETA_DB = "";


    public DbPrincipal(@Nullable Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
        CARPETA_DB = "/data/data/"+context.getPackageName()+"/databases/"+NOMBRE_DB;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +TABLA_USUARIOS +"(" +
                "us_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "us_nombre TEXT NOT NULL," +
                "us_contraseña TEXT NOT NULL," +
                "us_rango TEXT DEFAULT 'Cliente'," +
                "us_session INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLA_PRODUCTOS +"(" +
                "prod_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "prod_nombre TEXT NOT NULL," +
                "prod_categoria TEXT NOT NULL," +
                "prod_precio REAL NOT NULL," +
                "prod_existencia INTEGER NOT NULL," +
                "prod_preciodesc REAL NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLA_TICKETS +"(" +
                "ticket_id INTEGER PRIMARY KEY," +
                "ticket_prod TEXT NOT NULL," +
                "ticket_cant INTEGER NOT NULL," +
                "ticket_total REAL NOT NULL," +
                "ticket_fecha DATE DEFAULT CURRENT_DATE," +
                "ticket_nomus TEXT NOT NULL," +
                "ticket_iduser INTEGER NOT NULL," +
                "FOREIGN KEY(ticket_iduser) REFERENCES t_usuarios(us_id))");

        sqLiteDatabase.execSQL("INSERT INTO "+ TABLA_USUARIOS +
                " VALUES(1, 'admin', '1234', 'Administrador', 1)");

    }

    /*En caso de mejorar la version de la base de datos (VERSION_DB), esta funcion borrará las tablas de la
    version anterior y volverá a crear las tablas modificadas*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_TICKETS);
        onCreate(sqLiteDatabase);
    }

    public boolean verificarBD(){
        SQLiteDatabase verBD = null;
        try{
            verBD = SQLiteDatabase.openDatabase(CARPETA_DB, null, SQLiteDatabase.OPEN_READONLY);
            verBD.close();
        }catch (SQLiteException e){
            Log.e("Error", "Base de datos inexistente"+e.getMessage());
        }
        if(verBD != null)
            verBD.close();

        return verBD != null;
    }

}
