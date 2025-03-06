package com.proyecto.jessuri.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.proyecto.jessuri.MainActivity;
import com.proyecto.jessuri.R;
import com.proyecto.jessuri.adaptadores.ProductoLstAdapter;
import com.proyecto.jessuri.adaptadores.TicketLstAdapter;
import com.proyecto.jessuri.db.DbProductos;
import com.proyecto.jessuri.db.DbTickets;
import com.proyecto.jessuri.entidades.Tickets;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

    TextView txtvMensajeTickets;
    RecyclerView rcvTickets;
    DbTickets dbTickets;
    ArrayList<Tickets> ticketsArrayList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        //Codigo principal
        txtvMensajeTickets = view.findViewById(R.id.txtvMensajeCompras);
        rcvTickets = view.findViewById(R.id.rcvTickets);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = (TextView) headerView.findViewById(R.id.txtvTitulo);
        TextView navRango = (TextView) headerView.findViewById(R.id.txtvSubtitulo);
        TextView navId = (TextView) headerView.findViewById(R.id.txtvId);
        int id = Integer.parseInt(navId.getText().toString());
        String usuario = navUser.getText().toString(), rango = navRango.getText().toString();
        dbTickets = new DbTickets(getActivity());
        rcvTickets.setLayoutManager(new LinearLayoutManager(getActivity()));
        ticketsArrayList = new ArrayList<>();
        if(rango.equals("Cliente") || rango.equals("VIP")){
            TicketLstAdapter adapter = new TicketLstAdapter(dbTickets.mostrarTicketClientes(id), new TicketLstAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Tickets ticket) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Borrar ticket")
                            .setCancelable(true)
                            .setMessage("¿Desea borrar el ticket seleccionado?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dbTickets = new DbTickets(getActivity());
                                    boolean res = dbTickets.eliminarTicket(ticket.getIdTicket());
                                    if (res) {
                                        Toast.makeText(getActivity(), "Borrado con éxito", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("idUser", id);
                                        intent.putExtra("usuario", usuario);
                                        intent.putExtra("rango", rango);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Error al borrar...", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            });
            rcvTickets.setAdapter(adapter);
        }
        else {
            TicketLstAdapter adapter = new TicketLstAdapter(dbTickets.mostrarTicket(), new TicketLstAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Tickets ticket) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Borrar ticket")
                            .setCancelable(true)
                            .setMessage("¿Desea borrar el ticket seleccionado?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dbTickets = new DbTickets(getActivity());
                                    boolean res = dbTickets.eliminarTicket(ticket.getIdTicket());
                                    if (res) {
                                        Toast.makeText(getActivity(), "Borrado con éxito", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("idUser", id);
                                        intent.putExtra("usuario", usuario);
                                        intent.putExtra("rango", rango);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Error al borrar...", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            });
            rcvTickets.setAdapter(adapter);
        }
        int cantidad = rcvTickets.getAdapter().getItemCount();
        if(cantidad > 0){
            txtvMensajeTickets.setText("");
        }
        return view;
    }

}