package com.proyecto.jessuri.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
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

import com.google.android.material.navigation.NavigationView;
import com.proyecto.jessuri.MainActivity;
import com.proyecto.jessuri.R;
import com.proyecto.jessuri.adaptadores.ProductoLstAdapter;
import com.proyecto.jessuri.db.DbProductos;
import com.proyecto.jessuri.entidades.Productos;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosFragment newInstance() {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button btnAgregar;
    TextView txtvMensaje;
    RecyclerView rcvProds;
    ArrayList<Productos> productosArrayList;
    DbProductos dbProductos;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        //Codigo principal
        btnAgregar = view.findViewById(R.id.btnAgregarProd);
        txtvMensaje = view.findViewById(R.id.txtvMensaje);
        rcvProds = view.findViewById(R.id.rcvProductos);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProducto();
            }
        });

        dbProductos = new DbProductos(getActivity());
        rcvProds.setLayoutManager(new LinearLayoutManager(getActivity()));
        productosArrayList = new ArrayList<>();
        ProductoLstAdapter adapter = new ProductoLstAdapter(dbProductos.mostrarDatos(), new ProductoLstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Productos productos) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.opciones_producto);
                final Spinner spnOpcProd = dialog.findViewById(R.id.spnOpcProducto);
                final Spinner spnCateAct = dialog.findViewById(R.id.spnCategoriaAct);
                final LinearLayout lyProdEditar = dialog.findViewById(R.id.objetosProducto);
                final LinearLayout lyProdEliminar = dialog.findViewById(R.id.objetosEliminar);
                final EditText txtProdAct = dialog.findViewById(R.id.txtProductoAct);
                final EditText txtPrecioAct = dialog.findViewById(R.id.txtPrecioAct);
                final EditText txtExistenciaAct = dialog.findViewById(R.id.txtExistenciaAct);
                Button btnActualizarDatos = dialog.findViewById(R.id.btnAceptarCambios);
                spnOpcProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String opcion = spnOpcProd.getSelectedItem().toString();
                        if (opcion.equals("Editar")) {
                            lyProdEditar.setVisibility(View.VISIBLE);
                            lyProdEliminar.setVisibility(View.GONE);
                            txtProdAct.setText(productos.getNombreProducto());
                            txtPrecioAct.setText(String.format("%.2f", productos.getPrecioProducto()));
                            txtExistenciaAct.setText(String.valueOf(productos.getExistProducto()));
                        }
                        if (opcion.equals("Eliminar")) {
                            lyProdEditar.setVisibility(View.GONE);
                            lyProdEliminar.setVisibility(View.VISIBLE);

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
                        String opcion = spnOpcProd.getSelectedItem().toString();
                        if(opcion.equals("Escoge una opcion")){
                            Toast.makeText(context, "Selecciona una opcion valida\n" +
                                    "para continuar", Toast.LENGTH_SHORT).show();
                        }
                        if(opcion.equals("Editar")){
                            if(txtPrecioAct.getText().toString().equals("") || txtExistenciaAct.getText().toString().equals("")
                                    || txtProdAct.getText().toString().equals("")
                                    || spnCateAct.getSelectedItem().toString().equals("Seleccionar")) {
                                Toast.makeText(context, "Faltan datos que especificar", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String nombre = txtProdAct.getText().toString(),
                                        categoria = spnCateAct.getSelectedItem().toString();
                                double precio = Double.parseDouble(txtPrecioAct.getText().toString());
                                int id = productos.getIdProducto(),
                                existencia = Integer.parseInt(txtExistenciaAct.getText().toString());
                                DbProductos dbProductos = new DbProductos(context);
                                boolean res = dbProductos.actualizarProducto(id, nombre, categoria,
                                        precio, existencia);
                                if(res){
                                    Toast.makeText(context, "Producto actualizado", Toast.LENGTH_SHORT).show();
                                    actualizar();
                                    dialog.dismiss();
                                }
                                else{
                                    Toast.makeText(context, "Ocurrio un error...", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        }
                        if(opcion.equals("Eliminar")){
                            int id = productos.getIdProducto();
                            DbProductos dbProductos = new DbProductos(context);
                            boolean res = dbProductos.eliminarProducto(id);
                            if(res){
                                Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                                actualizar();
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
        rcvProds.setAdapter(adapter);
        int cantidad = rcvProds.getAdapter().getItemCount();
        if(cantidad > 0){
            txtvMensaje.setText("");
        }
        return view;
    }

    void agregarProducto(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.agregar_producto);
        final EditText txtNombreProd = dialog.findViewById(R.id.txtProdN);
        final EditText txtPrecio = dialog.findViewById(R.id.txtPrecioN);
        final EditText txtExistencia = dialog.findViewById(R.id.txtExistenciaN);
        final Spinner spnCategoria = dialog.findViewById(R.id.spnCateN);
        Button btnAgregar = dialog.findViewById(R.id.btnAñadirN);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtExistencia.getText().toString().equals("") || txtNombreProd.getText().toString().equals("") ||
                txtPrecio.getText().toString().equals("") || spnCategoria.getSelectedItem().toString().equals("Seleccionar")) {
                    Toast.makeText(getActivity(), "Faltan datos por especificar", Toast.LENGTH_SHORT).show();
                }
                else{
                    Context context = getActivity();
                    String nombreP = txtNombreProd.getText().toString(),
                            cateP = spnCategoria.getSelectedItem().toString();
                    int existencia = Integer.parseInt(txtExistencia.getText().toString());
                    double precioP = Double.parseDouble(txtPrecio.getText().toString());
                    DbProductos dbProductos = new DbProductos(context);
                    long id = dbProductos.agregarProducto(nombreP, cateP, precioP, existencia);
                    if (id > 0) {
                        Toast.makeText(context, "Producto añadido", Toast.LENGTH_SHORT).show();
                        actualizar();
                    } else {
                        Toast.makeText(context, "Error al añadir", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    public void actualizar() {
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