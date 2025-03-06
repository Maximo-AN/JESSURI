package com.proyecto.jessuri.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.proyecto.jessuri.entidades.Productos;

import java.util.ArrayList;

public class DbProductos extends DbPrincipal{
    Context context;
    public DbProductos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarProducto(String nombre, String categoria, double precio, int existencia){
        long id = 0;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db =  dbPrincipal.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("prod_nombre", nombre);
            values.put("prod_categoria", categoria);
            values.put("prod_precio", precio);
            values.put("prod_existencia", existencia);
            values.put("prod_preciodesc", precio-(precio*0.10));
            id = db.insert(TABLA_PRODUCTOS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Productos> mostrarDatos(){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        ArrayList<Productos> listaProd = new ArrayList<>();
        Productos productos;
        Cursor cursorProductos = db.rawQuery("SELECT * FROM "+TABLA_PRODUCTOS, null);
        int i = 0;
        if(cursorProductos.moveToFirst()){
            do {
                productos = new Productos();
                productos.setIdProducto(cursorProductos.getInt(0));
                productos.setNombreProducto(cursorProductos.getString(1));
                productos.setCategoriaProducto(cursorProductos.getString(2));
                productos.setPrecioProducto(cursorProductos.getDouble(3));
                productos.setExistProducto(cursorProductos.getInt(4));
                productos.setDescProducto(cursorProductos.getDouble(5));
                productos.setNumProducto(i+1);
                i++;
                listaProd.add(productos);
            }while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProd;
    }

    public ArrayList<Productos> mostrarDatosEsp(String categoria){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        ArrayList<Productos> listaProd = new ArrayList<>();
        Productos productos;
        Cursor cursorProductos = db.rawQuery("SELECT * FROM "+TABLA_PRODUCTOS
                +" WHERE prod_categoria = '"+categoria+"'", null);
        int i = 0;
        if(cursorProductos.moveToFirst()){
            do {
                productos = new Productos();
                productos.setIdProducto(cursorProductos.getInt(0));
                productos.setNombreProducto(cursorProductos.getString(1));
                productos.setCategoriaProducto(cursorProductos.getString(2));
                productos.setPrecioProducto(cursorProductos.getDouble(3));
                productos.setExistProducto(cursorProductos.getInt(4));
                productos.setDescProducto(cursorProductos.getDouble(5));
                productos.setNumProducto(i+1);
                i++;
                listaProd.add(productos);
            }while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProd;
    }

    public boolean eliminarProducto(int idProducto){
        boolean resultado = false;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+ TABLA_PRODUCTOS + " WHERE prod_id = '"+idProducto+"'");
            resultado = true;
        }catch (Exception ex){
            ex.toString();
            resultado = false;
        }
        return resultado;
    }

    public boolean actualizarProducto(int id, String nombre, String categoria, double precio, int existencia){
        boolean correcto = false;
        DbPrincipal dbHelper = new DbPrincipal(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE "+ TABLA_PRODUCTOS + " SET prod_nombre = '"+nombre+"', prod_categoria = '" +
                    categoria+ "', prod_precio = '"+precio+"', prod_existencia = '"+ existencia +"' WHERE prod_id = '"+id+"' ");
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
