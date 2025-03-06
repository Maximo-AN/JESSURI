package com.proyecto.jessuri.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.jessuri.R;
import com.proyecto.jessuri.entidades.Productos;

import java.util.ArrayList;

public class ProdVentaLstAdapter extends RecyclerView.Adapter<ProdVentaLstAdapter.ProdVentaViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(Productos item);
    }
    ArrayList<Productos> productosArrayList;
    private final OnItemClickListener listener;

    public ProdVentaLstAdapter (ArrayList<Productos> productosArrayList, OnItemClickListener listener){
        this.productosArrayList = productosArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productov_lst_item,
                parent, false);
        return new ProdVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdVentaViewHolder holder, int position) {
        holder.txtvNombre.setText(productosArrayList.get(position).getNombreProducto());
        holder.txtvCategoria.setText(productosArrayList.get(position).getCategoriaProducto());
        holder.txtvPrecio.setText("$ "+String.format("%.2f", productosArrayList.get(position).getPrecioProducto()));
        holder.txtvNumProd.setText(String.valueOf(productosArrayList.get(position).getNumProducto()));
        holder.txtvExistencia.setText("Existencia: "+ String.valueOf(productosArrayList.get(position).getExistProducto()));
        holder.comprarProducto(productosArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productosArrayList.size();
    }

    public class ProdVentaViewHolder extends RecyclerView.ViewHolder {
        TextView txtvNombre, txtvPrecio, txtvExistencia, txtvNumProd, txtvCategoria;
        public ProdVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvNombre = itemView.findViewById(R.id.txtvNombreProductoV);
            txtvPrecio = itemView.findViewById(R.id.txtvPrecioV);
            txtvExistencia = itemView.findViewById(R.id.txtvExistenciaV);
            txtvNumProd = itemView.findViewById(R.id.txtvNumProdV);
            txtvCategoria = itemView.findViewById(R.id.txtvCategoriaV);
        }

        public void comprarProducto(final Productos productos, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(productos);
                }
            });
        }

    }

}
