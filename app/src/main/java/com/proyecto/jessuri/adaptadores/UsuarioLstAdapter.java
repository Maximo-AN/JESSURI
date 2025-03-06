package com.proyecto.jessuri.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.jessuri.R;
import com.proyecto.jessuri.entidades.Usuarios;

import java.util.ArrayList;

public class UsuarioLstAdapter extends RecyclerView.Adapter<UsuarioLstAdapter.UsuarioViewHolder>{
    ArrayList<Usuarios> usuariosArrayList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Usuarios usuarios);
    }

    public UsuarioLstAdapter(ArrayList<Usuarios> usuariosArrayList, OnItemClickListener listener){
        this.usuariosArrayList = usuariosArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_lst_item,
                parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.txtvNombreU.setText(usuariosArrayList.get(position).getNombreUser());
        holder.txtvContra.setText(usuariosArrayList.get(position).getPswUser());
        holder.txtvRangoU.setText(usuariosArrayList.get(position).getRangoUser());
        holder.opcionesUsuario(usuariosArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return usuariosArrayList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView txtvNombreU, txtvRangoU, txtvContra;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvNombreU = itemView.findViewById(R.id.txtvNombreUser);
            txtvContra = itemView.findViewById(R.id.txtvContra);
            txtvRangoU = itemView.findViewById(R.id.txtvPuestoUser);
        }

        public void opcionesUsuario(final Usuarios usuarios, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(usuarios);
                }
            });
        }

    }
}
