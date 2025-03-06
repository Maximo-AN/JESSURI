package com.proyecto.jessuri.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.jessuri.R;
import com.proyecto.jessuri.entidades.Tickets;

import java.util.ArrayList;

public class TicketLstAdapter extends RecyclerView.Adapter<TicketLstAdapter.TicketViewHolder>{
    ArrayList<Tickets> ticketsArrayList;
    private final OnItemClickListener listener;
    public TicketLstAdapter(ArrayList<Tickets> ticketsArrayList, OnItemClickListener listener){
        this.ticketsArrayList = ticketsArrayList;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Tickets ticket);
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_lst_item,
                parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.txtvUsuario.setText("Compró: "+ticketsArrayList.get(position).getNombreTicket());
        holder.txtvFecha.setText("Generado el: "+ticketsArrayList.get(position).getFechaTicket());
        holder.txtvInfo.setText("Productos: "+ticketsArrayList.get(position).getProdTicket()
        +"\nCantidad: "+String.valueOf(ticketsArrayList.get(position).getCantidadTicket())
        +"\nTotal: $"+String.format("%.2f", ticketsArrayList.get(position).getTotalTicket()));
        holder.txtvNumTicket.setText(String.valueOf(ticketsArrayList.get(position).getNumTicket()));
        holder.opcionesTicket(ticketsArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ticketsArrayList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView txtvInfo, txtvFecha, txtvUsuario, txtvNumTicket;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvFecha = itemView.findViewById(R.id.txtvFecha);
            txtvInfo = itemView.findViewById(R.id.txtvInfo);
            txtvUsuario = itemView.findViewById(R.id.txtvUsuario);
            txtvNumTicket = itemView.findViewById(R.id.txtvNumTicket);
        }

        public void opcionesTicket(final Tickets tickets, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tickets);
                }
            });
        }

    }

}
