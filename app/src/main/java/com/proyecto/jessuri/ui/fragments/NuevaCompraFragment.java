package com.proyecto.jessuri.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.proyecto.jessuri.CrearTicket;
import com.proyecto.jessuri.MainActivity;
import com.proyecto.jessuri.R;
import com.proyecto.jessuri.adaptadores.ProdVentaLstAdapter;
import com.proyecto.jessuri.db.DbProductos;
import com.proyecto.jessuri.entidades.Productos;

import java.util.ArrayList;

public class NuevaCompraFragment extends Fragment {

    RecyclerView rcvProdV;
    Button btnRealizarVenta;
    ArrayList<Productos> productosArrayList;
    DbProductos dbProductos;
    RadioButton rdbCredito, rdbEfectivo;
    Spinner spnCategoria;
    CheckBox chkVIP;
    int existencia = 0;
    ArrayList<String> ticket = new ArrayList<>();
    double carrito = 0, precuenta = 0;
    String tipo = "";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevacompra, container, false);
        //Codigo principal
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rdgBotones);
        spnCategoria = view.findViewById(R.id.spnCategoria);
        chkVIP = view.findViewById(R.id.chkVIP);
        btnRealizarVenta = view.findViewById(R.id.btnRealizarV);
        rdbCredito = view.findViewById(R.id.rdbCredito);
        rdbEfectivo = view.findViewById(R.id.rdbEfectivo);
        rcvProdV = view.findViewById(R.id.rcvProdsV);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = (TextView) headerView.findViewById(R.id.txtvTitulo);
        TextView navRango = (TextView) headerView.findViewById(R.id.txtvSubtitulo);
        TextView navId = (TextView) headerView.findViewById(R.id.txtvId);
        int id = Integer.parseInt(navId.getText().toString());
        String usuario = navUser.getText().toString(), rango = navRango.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdbCredito:
                        tipo = "CREDITO";
                        break;
                    case R.id.rdbEfectivo:
                        tipo = "EFECTIVO";
                        break;
                }
            }
        });

        chkVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rango.equals("VIP") || rango.equals("Administrador")){
                    Toast.makeText(getActivity(), "Tendrás un 50% de descuento", Toast.LENGTH_SHORT).show();
                    chkVIP.setChecked(true);
                }
                else {
                    Toast.makeText(getActivity(), "No puedes seleccionar esta opcion", Toast.LENGTH_SHORT).show();
                    chkVIP.setChecked(false);
                }
            }
        });

        dbProductos = new DbProductos(getActivity());
        rcvProdV.setLayoutManager(new LinearLayoutManager(getActivity()));
        productosArrayList = new ArrayList<>();
        ProdVentaLstAdapter adapter = new ProdVentaLstAdapter(dbProductos.mostrarDatos(),
                new ProdVentaLstAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Productos item) {

                    }
                });
        rcvProdV.setAdapter(adapter);

        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoria = spnCategoria.getSelectedItem().toString();
                if(categoria.equals("Ver todo")){
                    dbProductos = new DbProductos(getActivity());
                    rcvProdV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    productosArrayList = new ArrayList<>();
                    ProdVentaLstAdapter adapter = new ProdVentaLstAdapter(dbProductos.mostrarDatos(),
                            new ProdVentaLstAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Productos item) {
                                        final Dialog dialog = new Dialog(getActivity());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(true);
                                        dialog.setContentView(R.layout.hacer_compra);
                                        double precio = item.getPrecioProducto();
                                        final EditText txtCantidad = dialog.findViewById(R.id.txtCantidad);
                                        final TextView txtvSubt = dialog.findViewById(R.id.txtvSubt);
                                        Button btnAgregarC = dialog.findViewById(R.id.btnAgregarC);
                                        txtvSubt.setText("$ "+ String.format("%.2f",item.getPrecioProducto()));
                                        txtCantidad.setText("0");
                                        txtCantidad.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                int cantidad;
                                                double operacion = 0;
                                                if(txtCantidad.getText().toString().equals("")) {
                                                    txtvSubt.setText("$00.00");
                                                }
                                                else{
                                                    cantidad = Integer.parseInt(txtCantidad.getText().toString());
                                                    operacion = precio * cantidad;
                                                    precuenta = operacion;
                                                    txtvSubt.setText("$ "+String.format("%.2f", operacion));
                                                }
                                            }
                                        });
                                        btnAgregarC.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Context context = getActivity();
                                                double cuenta = precuenta;
                                                if(txtCantidad.getText().toString().equals("0")
                                                || txtCantidad.getText().toString().equals("")){
                                                    Toast.makeText(context, "No puedes comprar 0 piezas...", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    carrito += cuenta;
                                                    Toast.makeText(context, "Llevas $ "+String.format("%.2f", carrito), Toast.LENGTH_SHORT).show();
                                                    ticket.add(item.getNombreProducto());
                                                    ticket.add(txtCantidad.getText().toString());
                                                    ticket.add(String.format("%.2f",item.getPrecioProducto()));
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        dialog.show();
                                    }
                            });
                    rcvProdV.setAdapter(adapter);
                }

                else{
                    dbProductos = new DbProductos(getActivity());
                    rcvProdV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    productosArrayList = new ArrayList<>();
                    ProdVentaLstAdapter adapter = new ProdVentaLstAdapter(dbProductos.mostrarDatosEsp(categoria),
                            new ProdVentaLstAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Productos item) {
                                    final Dialog dialog = new Dialog(getActivity());
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(true);
                                    dialog.setContentView(R.layout.hacer_compra);
                                    double precio = item.getPrecioProducto();
                                    final EditText txtCantidad = dialog.findViewById(R.id.txtCantidad);
                                    final TextView txtvSubt = dialog.findViewById(R.id.txtvSubt);
                                    Button btnAgregarC = dialog.findViewById(R.id.btnAgregarC);
                                    txtvSubt.setText("$ "+ String.format("%.2f",item.getPrecioProducto()));
                                    txtCantidad.setText("0");
                                    txtCantidad.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                            int cantidad;
                                            double operacion = 0;
                                            if(txtCantidad.getText().toString().equals("")) {
                                                txtvSubt.setText("$00.00");
                                            }
                                            else {
                                                cantidad = Integer.parseInt(txtCantidad.getText().toString());
                                                existencia = item.getExistProducto();
                                                existencia = existencia - cantidad;
                                                if (existencia < 0) {
                                                    Toast.makeText(getActivity(), "No puedes agregar " +
                                                            "más productos\nsobrepasando la existencia.", Toast.LENGTH_SHORT).show();
                                                    txtvSubt.setText("$00.00");
                                                    operacion = 0;
                                                    precuenta = operacion;
                                                } else {
                                                    operacion = precio * cantidad;
                                                    precuenta = operacion;
                                                    txtvSubt.setText("$ " + String.format("%.2f", operacion));
                                                }
                                            }
                                        }
                                    });
                                    btnAgregarC.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = getActivity();
                                            double cuenta = precuenta;
                                            if(txtCantidad.getText().toString().equals("0")
                                                    || txtCantidad.getText().toString().equals("")){
                                                Toast.makeText(context, "No puedes comprar 0 piezas...", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                if (existencia < 0) {
                                                    Toast.makeText(context, "No sobrepases la existencia...", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    carrito += cuenta;
                                                    Toast.makeText(context, "Llevas $ " + String.format("%.2f", carrito), Toast.LENGTH_SHORT).show();
                                                    ticket.add(item.getNombreProducto());
                                                    ticket.add(txtCantidad.getText().toString());
                                                    ticket.add(String.format("%.2f",item.getPrecioProducto()));
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                    rcvProdV.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRealizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carrito == 0) {
                    Toast.makeText(getActivity(), "Agrega un producto a tu carrito primero...", Toast.LENGTH_SHORT).show();
                } else if (!rdbCredito.isChecked() && !rdbEfectivo.isChecked()) {
                    Toast.makeText(getActivity(), "Elige un método de pago...", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Realizar compra")
                            .setCancelable(true)
                            .setMessage("¿Proceder a pagar $" + String.format("%.2f", carrito) + "?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getActivity(), CrearTicket.class);
                                    intent.putExtra("carrito", carrito);
                                    intent.putStringArrayListExtra("ticket", (ArrayList<String>) ticket);
                                    intent.putExtra("tipo", tipo);
                                    intent.putExtra("idUser", id);
                                    intent.putExtra("usuario", usuario);
                                    intent.putExtra("rango", rango);
                                    if(chkVIP.isChecked()){
                                        intent.putExtra("verVip", true);
                                    }
                                    getActivity().finish();
                                    startActivity(intent);
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
        });
        return view;
    }

}
