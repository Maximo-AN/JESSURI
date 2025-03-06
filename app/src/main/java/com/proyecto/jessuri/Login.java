package com.proyecto.jessuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.jessuri.db.DbUsuarios;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText txtaUsuario, txtaContraseña;
    Button btnReg, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEntrar = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegistro);
        txtaContraseña = findViewById(R.id.txtaPassword2);
        txtaUsuario = findViewById(R.id.txtaUser2);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDatos();
            }
        });
    }

    public void registro(){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    public void verificarDatos(){
        if(validarCampos()){
            String user = txtaUsuario.getText().toString(),
                    contra = txtaContraseña.getText().toString();
            DbUsuarios dbUsuarios = new DbUsuarios(this);
            ArrayList<Usuarios> usuarios = dbUsuarios.buscarUsuario(user, contra);
            for(Usuarios us: usuarios){
                if(us.getNombreUser().equals(user) && us.getPswUser().equals(contra)){
                    int id = usuarios.get(0).getIdUser();
                    dbUsuarios.modificarSesion(2, id);
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("idUser", id);
                    i.putExtra("usuario", user);
                    i.putExtra("rango", us.getRangoUser());
                    startActivity(i);
                    finish();
                }
                else if(us.getIdUser() == 0){
                    Toast.makeText(this, "Usuario inexistente ó\nContraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean validarCampos(){
        boolean retorno = true;
        if(txtaUsuario.getText().toString().equals("") ||
                txtaContraseña.getText().toString().equals(""))
            retorno = false;
        return retorno;
    }

}