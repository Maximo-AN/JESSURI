package com.proyecto.jessuri.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.proyecto.jessuri.MainActivity;
import com.proyecto.jessuri.R;
import com.proyecto.jessuri.adaptadores.ProductoLstAdapter;
import com.proyecto.jessuri.adaptadores.UsuarioLstAdapter;
import com.proyecto.jessuri.db.DbProductos;
import com.proyecto.jessuri.db.DbUsuarios;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;


public class UsuariosFragment extends Fragment {

    Button btnAñadir;
    RecyclerView rcvUsers;
    ArrayList<Usuarios> usuariosArrayList;
    DbUsuarios dbUsuarios;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);
        //Codigo principal
        btnAñadir = view.findViewById(R.id.btnAñadirUser);
        rcvUsers = view.findViewById(R.id.rcvUsuarios);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarUsuario();
            }
        });

        dbUsuarios = new DbUsuarios(getActivity());
        rcvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        usuariosArrayList = new ArrayList<>();
        UsuarioLstAdapter adapter = new UsuarioLstAdapter(dbUsuarios.mostrarUsuarios(), new UsuarioLstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Usuarios usuarios) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.opciones_usuario);
                final Spinner spnOpcUser = dialog.findViewById(R.id.spnOpcUser);
                final Spinner spnRangoAct = dialog.findViewById(R.id.spnRangoAct);
                final LinearLayout lyUserEditar = dialog.findViewById(R.id.objetosUsuario);
                final LinearLayout lyUserEliminar = dialog.findViewById(R.id.objetosBorrarUser);
                final EditText txtUserAct = dialog.findViewById(R.id.txtUserAct);
                final EditText txtPswAct = dialog.findViewById(R.id.txtPswAct);
                Button btnActualizarDatos = dialog.findViewById(R.id.btnAceptarCUser);
                spnOpcUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String opcion = spnOpcUser.getSelectedItem().toString();
                        if (opcion.equals("Editar")) {
                            lyUserEditar.setVisibility(View.VISIBLE);
                            lyUserEliminar.setVisibility(View.GONE);
                            txtUserAct.setText(usuarios.getNombreUser());
                            txtPswAct.setText(usuarios.getPswUser());
                        }
                        if (opcion.equals("Eliminar")) {
                            lyUserEditar.setVisibility(View.GONE);
                            lyUserEliminar.setVisibility(View.VISIBLE);

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = getActivity();
                        String opcion = spnOpcUser.getSelectedItem().toString();
                        if(opcion.equals("Escoge una opcion")){
                            Toast.makeText(context, "Selecciona una opcion valida\n" +
                                    "para continuar", Toast.LENGTH_SHORT).show();
                        }
                        if(opcion.equals("Editar")){
                            if(txtUserAct.getText().toString().equals("") || txtPswAct.getText().toString().equals("")
                                    || spnRangoAct.getSelectedItem().toString().equals("Seleccionar")) {
                                Toast.makeText(context, "Faltan datos que especificar", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String nombre = txtUserAct.getText().toString(),
                                        rango = spnRangoAct.getSelectedItem().toString(),
                                        psw = txtPswAct.getText().toString();
                                int id = usuarios.getIdUser();
                                dbUsuarios = new DbUsuarios(context);
                                boolean res = dbUsuarios.actualizarUsuario(id, nombre, psw,
                                        rango);
                                if(res){
                                    Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show();
                                    refrescar();
                                    dialog.dismiss();
                                }
                                else{
                                    Toast.makeText(context, "Ocurrio un error...", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        }
                        if(opcion.equals("Eliminar")){
                            int id = usuarios.getIdUser();
                            dbUsuarios = new DbUsuarios(context);
                            boolean res = dbUsuarios.eliminarUsuario(id);
                            if(res){
                                Toast.makeText(context, "Usuario borrado", Toast.LENGTH_SHORT).show();
                                refrescar();
                                dialog.dismiss();
                            }
                            else{
                                Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
        rcvUsers.setAdapter(adapter);
        return view;
    }

    void agregarUsuario(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.agregar_usuario);
        final EditText txtNombreEmp = dialog.findViewById(R.id.txtUsuarioN);
        final EditText txtContraEmp = dialog.findViewById(R.id.txtContraseñaN);
        final Spinner spnPuesto = dialog.findViewById(R.id.spnRangoN);
        Button btnAñadir = dialog.findViewById(R.id.btnAñadirUsN);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNombreEmp.getText().toString().equals("") || txtContraEmp.getText().toString().equals("") ||
                    spnPuesto.getSelectedItem().toString().equals("Seleccionar")) {
                    Toast.makeText(getActivity(), "Faltan datos por especificar", Toast.LENGTH_SHORT).show();
                } else {
                    Context context = getActivity();
                    String nombreEmp = txtNombreEmp.getText().toString(),
                            contraEmp = txtContraEmp.getText().toString(),
                            puestoEmp = spnPuesto.getSelectedItem().toString();
                    dbUsuarios = new DbUsuarios(context);
                    long id = dbUsuarios.insertarUsuario2(nombreEmp, contraEmp, puestoEmp);
                    if (id > 0) {
                        Toast.makeText(getActivity(), "Empleado añadido", Toast.LENGTH_SHORT).show();
                        refrescar();
                    } else
                        Toast.makeText(getActivity(), "Error al añadir empleado", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void refrescar(){
        Intent i = new Intent(getActivity(), MainActivity.class);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = (TextView) headerView.findViewById(R.id.txtvTitulo);
        TextView navRango = (TextView) headerView.findViewById(R.id.txtvSubtitulo);
        TextView navId = (TextView) headerView.findViewById(R.id.txtvId);
        int id = Integer.parseInt(navId.getText().toString());
        String usuario = navUser.getText().toString(), rango = navRango.getText().toString();
        i.putExtra("idUser", id);
        i.putExtra("usuario", usuario);
        i.putExtra("rango", rango);
        getActivity().finish();
        startActivity(i);
    }

}