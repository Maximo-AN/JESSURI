package com.proyecto.jessuri.entidades;

public class Productos {
    private int idProducto;
    private int numProducto;
    private int existProducto;
    private String nombreProducto;
    private String categoriaProducto;
    private double precioProducto;
    private double descProducto;

    public int getExistProducto() {
        return existProducto;
    }

    public void setExistProducto(int existProducto) {
        this.existProducto = existProducto;
    }

    public int getNumProducto() {
        return numProducto;
    }

    public void setNumProducto(int numProducto) {
        this.numProducto = numProducto;
    }

    public double getDescProducto() {
        return descProducto;
    }
    public void setDescProducto(double descProducto) {
        this.descProducto = descProducto;
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }
}
