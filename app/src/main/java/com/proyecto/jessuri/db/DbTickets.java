package com.proyecto.jessuri.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.proyecto.jessuri.entidades.Productos;
import com.proyecto.jessuri.entidades.Tickets;

import java.util.ArrayList;

public class DbTickets extends DbPrincipal{
    Context context;
    public DbTickets(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarTicket(String productos, int cantidad, double total, String user, int idUser){
        long id = 0;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db =  dbPrincipal.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("ticket_prod", productos);
            values.put("ticket_cant", cantidad);
            values.put("ticket_total", total);
            values.put("ticket_nomus", user);
            values.put("ticket_iduser", idUser);
            id = db.insert(TABLA_TICKETS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Tickets> mostrarTicket(){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        ArrayList<Tickets> listaTickets = new ArrayList<>();
        Tickets tickets;
        Cursor cursorTickets = db.rawQuery("SELECT * FROM "+TABLA_TICKETS, null);
        int i = 0;
        if(cursorTickets.moveToFirst()){
            do {
                tickets = new Tickets();
                tickets.setIdTicket(cursorTickets.getInt(0));
                tickets.setProdTicket(cursorTickets.getString(1));
                tickets.setCantidadTicket(cursorTickets.getInt(2));
                tickets.setTotalTicket(cursorTickets.getDouble(3));
                tickets.setFechaTicket(cursorTickets.getString(4));
                tickets.setNombreTicket(cursorTickets.getString(5));
                tickets.setIdUserTicket(cursorTickets.getInt(6));
                tickets.setNumTicket(i+1);
                i++;
                listaTickets.add(tickets);
            }while (cursorTickets.moveToNext());
        }
        cursorTickets.close();
        return listaTickets;
    }

    public ArrayList<Tickets> mostrarTicketClientes(int idCliente){
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        ArrayList<Tickets> listaTickets = new ArrayList<>();
        Tickets tickets;
        Cursor cursorTickets = db.rawQuery("SELECT * FROM "+TABLA_TICKETS+" WHERE ticket_iduser = '"+idCliente+"'", null);
        int i = 0;
        if(cursorTickets.moveToFirst()){
            do {
                tickets = new Tickets();
                tickets.setIdTicket(cursorTickets.getInt(0));
                tickets.setProdTicket(cursorTickets.getString(1));
                tickets.setCantidadTicket(cursorTickets.getInt(2));
                tickets.setTotalTicket(cursorTickets.getDouble(3));
                tickets.setFechaTicket(cursorTickets.getString(4));
                tickets.setNombreTicket(cursorTickets.getString(5));
                tickets.setIdUserTicket(cursorTickets.getInt(6));
                tickets.setNumTicket(i+1);
                i++;
                listaTickets.add(tickets);
            }while (cursorTickets.moveToNext());
        }
        cursorTickets.close();
        return listaTickets;
    }

    public boolean eliminarTicket(int idTicket){
        boolean resultado = false;
        DbPrincipal dbPrincipal = new DbPrincipal(context);
        SQLiteDatabase db = dbPrincipal.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+ TABLA_TICKETS + " WHERE ticket_id = '"+idTicket+"'");
            resultado = true;
        }catch (Exception ex){
            ex.toString();
            resultado = false;
        }
        return resultado;
    }

}
