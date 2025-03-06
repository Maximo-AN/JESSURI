package com.proyecto.jessuri.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.proyecto.jessuri.entidades.Productos;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;

public class DbUsuarios extends DbPrincipal{
    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String nombre, String psw){
        long Id = 0;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        Cursor cursorVer = null;
        cursorVer = db.rawQuery("SELECT * FROM "+TABLA_USUARIOS+" WHERE us_nombre = '"+nombre+"'", null);
        if(!cursorVer.moveToNext()) {
            try {
                ContentValues values = new ContentValues();
                values.put("us_nombre", nombre);
                values.put("us_contraseña", psw);
                values.put("us_session", 1);

                Id = db.insert(TABLA_USUARIOS, null, values);
            } catch (Exception ex) {
                ex.toString();
            }
        }
        return Id;
    }

    public long insertarUsuario2(String nombre, String psw, String rango){
        long Id = 0;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        Cursor cursorVer = null;
        cursorVer = db.rawQuery("SELECT * FROM "+TABLA_USUARIOS+" WHERE us_nombre = '"+nombre+"'", null);
        if(!cursorVer.moveToNext()) {
            try {
                ContentValues values = new ContentValues();
                values.put("us_nombre", nombre);
                values.put("us_contraseña", psw);
                values.put("us_rango", rango);
                values.put("us_session", 1);

                Id = db.insert(TABLA_USUARIOS, null, values);
            } catch (Exception ex) {
                ex.toString();
            }
        }
        return Id;
    }

    public ArrayList<Usuarios> buscarUsuario(String nombreUs, String pswUs){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();

        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuarios = null;
        Cursor cursorUsuarios = null;

        cursorUsuarios = db.rawQuery("SELECT * FROM "+ TABLA_USUARIOS+ " WHERE us_nombre = '"+nombreUs+"' AND us_contraseña = '"+pswUs+"'", null);
        if(cursorUsuarios.moveToFirst()) {
            do {
                usuarios = new Usuarios();
                usuarios.setIdUser(cursorUsuarios.getInt(0));
                usuarios.setNombreUser(cursorUsuarios.getString(1));
                usuarios.setPswUser(cursorUsuarios.getString(2));
                usuarios.setRangoUser(cursorUsuarios.getString(3));
                listaUsuarios.add(usuarios);
            }while (cursorUsuarios.moveToNext());
        }
        else{
            usuarios = new Usuarios();
            usuarios.setIdUser(0);
            usuarios.setNombreUser("ERROR");
            usuarios.setPswUser("ERROR");
            listaUsuarios.add(usuarios);
        }
        cursorUsuarios.close();
        return listaUsuarios;
    }

    public boolean modificarSesion(int idSes, int idUser){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db =  dbPrincipal.getWritableDatabase();
        boolean confirmacion;
        try {
            db.execSQL("UPDATE " + TABLA_USUARIOS + " SET us_session = '"+ idSes +"' WHERE us_id = '" + idUser + "'");
            confirmacion = true;
        }catch (Exception ex){
            ex.toString();
            confirmacion = false;
        }finally {
            db.close();
        }
        return confirmacion;
    }

    public ArrayList<Usuarios> verificarSesion(){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db =  dbPrincipal.getWritableDatabase();

        ArrayList<Usuarios> listaUsuario = new ArrayList<>();
        Cursor cursorUsuario = null;
        Usuarios usuario = null;

        cursorUsuario = db.rawQuery("SELECT * FROM "+ TABLA_USUARIOS +" WHERE us_session = 2 LIMIT 1", null);
        if(cursorUsuario.moveToFirst()) {
            usuario = new Usuarios();
            usuario.setIdUser(cursorUsuario.getInt(0));
            usuario.setNombreUser(cursorUsuario.getString(1));
            usuario.setPswUser(cursorUsuario.getString(2));
            usuario.setRangoUser(cursorUsuario.getString(3));
            usuario.setSesionUs(cursorUsuario.getInt(4));
            listaUsuario.add(usuario);
        }
        else {
            usuario = new Usuarios();
            usuario.setSesionUs(0);
            listaUsuario.add(usuario);
        }
        cursorUsuario.close();

        return listaUsuario;
    }

    public ArrayList<Usuarios> mostrarUsuarios(){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuarios;
        Cursor cursorUsuarios = db.rawQuery("SELECT * FROM "+TABLA_USUARIOS, null);
        int i = 0;
        if(cursorUsuarios.moveToFirst()){
            do {
                usuarios = new Usuarios();
                usuarios.setIdUser(cursorUsuarios.getInt(0));
                usuarios.setNombreUser(cursorUsuarios.getString(1));
                usuarios.setPswUser(cursorUsuarios.getString(2));
                usuarios.setRangoUser(cursorUsuarios.getString(3));
                usuarios.setSesionUs(cursorUsuarios.getInt(4));
                usuarios.setNumUser(i+1);
                i++;
                listaUsuarios.add(usuarios);
            }while (cursorUsuarios.moveToNext());
        }
        cursorUsuarios.close();
        return listaUsuarios;
    }

    public boolean eliminarUsuario(int idUsuario){
        boolean resultado = false;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+ TABLA_USUARIOS + " WHERE us_id = '"+idUsuario+"'");
            resultado = true;
        }catch (Exception ex){
            ex.toString();
            resultado = false;
        }
        return resultado;
    }

    public boolean actualizarUsuario(int id, String nombre, String psw, String rango){
        boolean correcto = false;
        DbPrincipal dbHelper = new DbPrincipal(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE "+ TABLA_USUARIOS + " SET us_nombre = '"+nombre+"', us_contraseña = '" +
                    psw+ "', us_rango = '"+rango+"' WHERE us_id = '"+id+"' ");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }
        finally {
            db.close();
        }
        return correcto;
    }

}
