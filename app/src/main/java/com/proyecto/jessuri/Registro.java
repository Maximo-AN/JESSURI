package com.proyecto.jessuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.jessuri.R;
import com.proyecto.jessuri.db.DbUsuarios;

public class Registro extends AppCompatActivity {
    EditText txtaUser, txtaPsw, txtaPswVer;
    Button btnRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtaUser = findViewById(R.id.txtaUser);
        txtaPsw = findViewById(R.id.txtaPassword);
        txtaPswVer = findViewById(R.id.txtaVPass);
        btnRegistrarse = findViewById(R.id.btnReg);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });
    }

    public void enviarDatos(){
        if(txtaPswVer.getText().toString().equals("") || txtaPsw.getText().toString().equals("") ||
        txtaUser.getText().toString().equals("")){
            Toast.makeText(this, "No deje ningun campo vacio", Toast.LENGTH_SHORT).show();
        }
        else if (!txtaPsw.getText().toString().equals(txtaPswVer.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else{
            DbUsuarios dbUsuarios =  new DbUsuarios(this);
            String user = txtaUser.getText().toString(),
                    contra = txtaPsw.getText().toString();
            long id = dbUsuarios.insertarUsuario(user, contra);
            if(id > 0){
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent i =  new Intent(this, Login.class);
                finish();
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Error\nEl usuario ya existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

}