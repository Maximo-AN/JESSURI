package com.proyecto.jessuri;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.jessuri.databinding.ActivityMainBinding;
import com.proyecto.jessuri.db.DbUsuarios;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    boolean isOpen = false;
    int idU;
    String user, rango;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        idU = bundle.getInt("idUser");
        user = bundle.getString("usuario");
        rango = bundle.getString("rango");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        fabOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_anim);
        fabClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_anim);
        rotateForward = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_cerrar_anim);
        rotateBackward = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_abrir_anim);
        binding.appBarMain.fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animacionBoton();
            }
        });

        binding.appBarMain.fabWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sitioWeb();
            }
        });

        binding.appBarMain.fabAcercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acercaDe();
                Toast.makeText(MainActivity.this, "Gracias por su preferencia :)", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_productos, R.id.nav_usuarios, R.id.nav_tickets, R.id.nav_nuevacompra, R.id.nav_cerrars)
                .setOpenableLayout(drawer)
                .build();
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = (TextView) headerView.findViewById(R.id.txtvTitulo);
        TextView navRango = (TextView) headerView.findViewById(R.id.txtvSubtitulo);
        TextView navId = (TextView) headerView.findViewById(R.id.txtvId);
        navUser.setText(user);
        navRango.setText(rango);
        navId.setText(String.valueOf(idU));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_cerrars).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Cerrar Sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Context context = MainActivity.this;
                                DbUsuarios dbUsuarios = new DbUsuarios(context);
                                boolean resultado = dbUsuarios.modificarSesion(1, idU);
                                if(resultado){
                                    Toast.makeText(MainActivity.this,
                                            "Sesión Cerrada...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity.this,
                                            "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                }
                                dialogInterface.dismiss();
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        if(navRango.getText().equals("Administrador")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_productos).setVisible(true);
            nav_Menu.findItem(R.id.nav_usuarios).setVisible(true);
        }
    }

    public void acercaDe(){
        LayoutInflater caratula = getLayoutInflater();
        Toast mostrar = new Toast(getApplicationContext());
        View vista = caratula.inflate(R.layout.acercade, null);
        mostrar.setDuration(Toast.LENGTH_LONG);
        mostrar.setView(vista);
        mostrar.setGravity(Gravity.CENTER, 0, 0);
        mostrar.show();
    }


    private void animacionBoton(){
        if(isOpen){
            binding.appBarMain.fabMenu.startAnimation(rotateForward);
            binding.appBarMain.fabWeb.startAnimation(fabClose);
            binding.appBarMain.fabAcercade.startAnimation(fabClose);
            binding.appBarMain.fabAcercade.setVisibility(View.GONE);
            binding.appBarMain.fabWeb.setVisibility(View.GONE);
            binding.appBarMain.fabWeb.setClickable(false);
            binding.appBarMain.fabAcercade.setClickable(false);
            isOpen = false;
        }
        else{
            binding.appBarMain.fabMenu.startAnimation(rotateBackward);
            binding.appBarMain.fabWeb.startAnimation(fabOpen);
            binding.appBarMain.fabAcercade.startAnimation(fabOpen);
            binding.appBarMain.fabAcercade.setVisibility(View.VISIBLE);
            binding.appBarMain.fabWeb.setVisibility(View.VISIBLE);
            binding.appBarMain.fabWeb.setClickable(true);
            binding.appBarMain.fabAcercade.setClickable(true);
            isOpen = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void sitioWeb(){
        Intent i = new Intent(MainActivity.this, SitioWeb.class);
        i.putExtra("idUser", idU);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Salir")
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        dialogInterface.dismiss();
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

}