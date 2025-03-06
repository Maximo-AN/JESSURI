package com.proyecto.jessuri.entidades;

public class Usuarios {
    private int idUser;
    private int numUser;
    private String nombreUser;
    private String pswUser;
    private String rangoUser;
    private int sesionUs;

    public int getNumUser() {
        return numUser;
    }

    public void setNumUser(int numUser) {
        this.numUser = numUser;
    }

    public String getRangoUser() {
        return rangoUser;
    }

    public void setRangoUser(String rangoUser) {
        this.rangoUser = rangoUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getPswUser() {
        return pswUser;
    }

    public void setPswUser(String pswUser) {
        this.pswUser = pswUser;
    }

    public int getSesionUs() {
        return sesionUs;
    }

    public void setSesionUs(int sesionUs) {
        this.sesionUs = sesionUs;
    }
}
