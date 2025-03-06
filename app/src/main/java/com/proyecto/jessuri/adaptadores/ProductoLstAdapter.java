package com.proyecto.jessuri.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.jessuri.R;
import com.proyecto.jessuri.entidades.Productos;

import java.util.ArrayList;

public class ProductoLstAdapter extends RecyclerView.Adapter<ProductoLstAdapter.ProductoViewHolder>{
    ArrayList<Productos> productosArrayList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Productos productos);
    }

    public ProductoLstAdapter(ArrayList<Productos> productosArrayList, OnItemClickListener listener){
        this.productosArrayList = productosArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_lst_item,
                parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.txtvIdProd.setText(String.valueOf(productosArrayList.get(position).getNumProducto()));
        holder.txtvNombreProd.setText(productosArrayList.get(position).getNombreProducto());
        holder.txtvCategoria.setText(productosArrayList.get(position).getCategoriaProducto());
        holder.txtvPrecio.setText("$ "+String.format("%.2f", productosArrayList.get(position).getPrecioProducto()));
        holder.opcionesProducto(productosArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productosArrayList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtvIdProd, txtvNombreProd, txtvPrecio, txtvCategoria, txtvPrecioDesc;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvCategoria = itemView.findViewById(R.id.txtvCategoria);
            txtvIdProd = itemView.findViewById(R.id.txtvIdProd);
            txtvNombreProd = itemView.findViewById(R.id.txtvNombreProducto);
            txtvPrecio = itemView.findViewById(R.id.txtvPrecio);
        }

        public void opcionesProducto(final Productos productos, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(productos);
                }
            });
        }

    }

}
