package com.proyecto.jessuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.jessuri.db.DbTickets;

import java.util.ArrayList;

public class CrearTicket extends AppCompatActivity {
    EditText txtmTicket;
    Button btnSalir;
    ArrayList<String> verTicket = new ArrayList<>();
    String ticket = "\t\t\t\tSUS COMPRAS FUERON:\n",
            tipo, usuario, rango;
    boolean verVip = false;
    int idU;
    double subtotal = 0, desc = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ticket);
        btnSalir = findViewById(R.id.btnSalir);
        txtmTicket = findViewById(R.id.txtmTicket);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirTicket();
            }
        });

        Bundle bundle = getIntent().getExtras();
        subtotal = bundle.getDouble("carrito");
        verTicket = bundle.getStringArrayList("ticket");
        tipo = bundle.getString("tipo");
        usuario = bundle.getString("usuario");
        rango = bundle.getString("rango");
        idU = bundle.getInt("idUser");
        verVip = bundle.getBoolean("verVip", false);
        int contador = 0;
        ticket = ticket+"Productos: \n";
        for(int z = 0; z < verTicket.size(); z+=3){
            ticket = ticket+"Producto "+(contador+1)+": "+verTicket.get(z)+"\n"
                    /*El "%verticket.size()" es para poder acceder a cierto lugar de
                    un arraylist correctamente sin que dé un error*/
                    +"Cantidad: "+verTicket.get((z+1)%verTicket.size())+"\n"
                    +"Precio: $"+verTicket.get((z+2)%verTicket.size())+"\n";
                    contador++;
        }
        if(tipo.equals("CREDITO")){
            desc = subtotal*0.10;
        } else if (tipo.equals("EFECTIVO")) {
            desc = subtotal*0.05;
        }

        if(verVip){
            double descuentoesp = subtotal*0.40;
            subtotal = subtotal-descuentoesp;
            ticket = ticket+"Especial VIP (-40%): $"+String.format("%.2f", descuentoesp)+"\n";
        }
        ticket = ticket+"Tipo de pago: "+tipo+"\nSubtotal: $"+String.format("%.2f", subtotal)
                +"\nDescuento: $"+String.format("%.2f", desc)
                +"\nTotal: $"+String.format("%.2f", subtotal-desc);
        txtmTicket.setText(ticket);
    }

    public void subirTicket(){
        DbTickets dbTickets = new DbTickets(CrearTicket.this);
        String productos = "";
        int cantidad = 0;
        for (int c = 0; c < verTicket.size(); c+=3){
            productos = productos+verTicket.get(c)+" | ";
            String dato = verTicket.get((c+1)%verTicket.size());
            int suma = Integer.parseInt(dato);
            cantidad = cantidad+suma;
        }
        long id = dbTickets.agregarTicket(productos, cantidad, subtotal-desc, usuario, idU);
        if(id > 0){
            Toast.makeText(this, "Ticket guardado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CrearTicket.this, MainActivity.class);
            i.putExtra("idUser", idU);
            i.putExtra("usuario", usuario);
            i.putExtra("rango", rango);
            finish();
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

}