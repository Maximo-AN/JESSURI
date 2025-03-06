package com.proyecto.jessuri.entidades;

public class Tickets {
    private int idTicket;
    private String prodTicket;
    private int cantidadTicket;
    private double totalTicket;
    private String fechaTicket;
    private String nombreTicket;
    private int idUserTicket;
    private int numTicket;

    public int getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getProdTicket() {
        return prodTicket;
    }

    public void setProdTicket(String prodTicket) {
        this.prodTicket = prodTicket;
    }

    public int getCantidadTicket() {
        return cantidadTicket;
    }

    public void setCantidadTicket(int cantidadTicket) {
        this.cantidadTicket = cantidadTicket;
    }

    public double getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(double totalTicket) {
        this.totalTicket = totalTicket;
    }

    public String getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(String fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public String getNombreTicket() {
        return nombreTicket;
    }

    public void setNombreTicket(String nombreTicket) {
        this.nombreTicket = nombreTicket;
    }

    public int getIdUserTicket() {
        return idUserTicket;
    }

    public void setIdUserTicket(int idUserTicket) {
        this.idUserTicket = idUserTicket;
    }
}
