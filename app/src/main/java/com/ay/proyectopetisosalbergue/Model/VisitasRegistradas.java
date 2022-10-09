package com.ay.proyectopetisosalbergue.Model;

public class VisitasRegistradas {
    private int idVisita;
    private int idCanino;
    private int idAlbergue;
    private String nomAlbergue;
    private String caninoNombre;
    private String caninoImg;
    private String visitaFecha;
    private String visitaHora;
    private String visitaEstado;

    public VisitasRegistradas() {
        this.idVisita = idVisita;
        this.caninoNombre = caninoNombre;
        this.caninoImg = caninoImg;
        this.visitaFecha = visitaFecha;
        this.visitaHora = visitaHora;
        this.visitaEstado = visitaEstado;
        this.idAlbergue = idAlbergue;
        this.nomAlbergue = nomAlbergue;
        this.idCanino = idCanino;
    }

    public int getIdCanino() {
        return idCanino;
    }

    public void setIdCanino(int idCanino) {
        this.idCanino = idCanino;
    }

    public String getNomAlbergue() {
        return nomAlbergue;
    }

    public void setNomAlbergue(String nomAlbergue) {
        this.nomAlbergue = nomAlbergue;
    }

    public int getIdAlbergue() {
        return idAlbergue;
    }

    public void setIdAlbergue(int idAlbergue) {
        this.idAlbergue = idAlbergue;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getCaninoNombre() {
        return caninoNombre;
    }

    public void setCaninoNombre(String caninoNombre) {
        this.caninoNombre = caninoNombre;
    }

    public String getCaninoImg() {
        return caninoImg;
    }

    public void setCaninoImg(String caninoImg) {
        this.caninoImg = caninoImg;
    }

    public String getVisitaFecha() {
        return visitaFecha;
    }

    public void setVisitaFecha(String visitaFecha) {
        this.visitaFecha = visitaFecha;
    }

    public String getVisitaHora() {
        return visitaHora;
    }

    public void setVisitaHora(String visitaHora) {
        this.visitaHora = visitaHora;
    }

    public String getVisitaEstado() {
        return visitaEstado;
    }

    public void setVisitaEstado(String visitaEstado) {
        this.visitaEstado = visitaEstado;
    }
}
