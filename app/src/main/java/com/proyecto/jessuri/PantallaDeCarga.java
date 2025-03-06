package com.proyecto.jessuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.proyecto.jessuri.db.DbPrincipal;
import com.proyecto.jessuri.db.DbUsuarios;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;

public class PantallaDeCarga extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DbPrincipal dbPrincipal = new DbPrincipal(PantallaDeCarga.this);
                if(dbPrincipal.verificarBD()) {
                    DbUsuarios dbUsuarios = new DbUsuarios(PantallaDeCarga.this);
                    ArrayList<Usuarios> user = dbUsuarios.verificarSesion();
                    for(Usuarios us:user) {
                        if (us.getSesionUs() == 2) {
                            int id = user.get(0).getIdUser();
                            dbUsuarios.modificarSesion(2, id);
                            Intent intent = new Intent(PantallaDeCarga.this, MainActivity.class);
                            intent.putExtra("idUser", id);
                            intent.putExtra("usuario", us.getNombreUser());
                            intent.putExtra("rango", us.getRangoUser());
                            startActivity(intent);
                        }
                        else {
                            Intent intento = new Intent(PantallaDeCarga.this, Login.class);
                            startActivity(intento);
                        }
                    }
                }
                else {
                    Intent intento = new Intent(PantallaDeCarga.this, Registro.class);
                    startActivity(intento);
                }
                finish();
            }
        },3500);

        /*DbPrincipal dbPrincipal2 = new DbPrincipal(PantallaDeCarga.this);
        if(dbPrincipal2.verificarBD()) {
            txtTexto.setText("Hey...\n¿Qué tal?");
            txtTexto.setGravity(Gravity.CENTER);
        }
        else {
            txtTexto.setText("¡Bienvenid@!");
        }*/
    }
}