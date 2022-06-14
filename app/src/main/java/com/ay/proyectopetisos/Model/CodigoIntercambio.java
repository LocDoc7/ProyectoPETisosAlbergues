package com.ay.proyectopetisos.Model;

public class CodigoIntercambio {
    private int idVenta;
    private String nomProductoIntercambio;
    private String nomAlbergueIntercambio;
    private String fechaValidaProductoIntercambio;
    private String imgProductoIntercambio;

    public CodigoIntercambio() {
        this.idVenta = idVenta;
        this.nomProductoIntercambio = nomProductoIntercambio;
        this.nomAlbergueIntercambio = nomAlbergueIntercambio;
        this.fechaValidaProductoIntercambio = fechaValidaProductoIntercambio;
        this.imgProductoIntercambio = imgProductoIntercambio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNomProductoIntercambio() {
        return nomProductoIntercambio;
    }

    public void setNomProductoIntercambio(String nomProductoIntercambio) {
        this.nomProductoIntercambio = nomProductoIntercambio;
    }

    public String getNomAlbergueIntercambio() {
        return nomAlbergueIntercambio;
    }

    public void setNomAlbergueIntercambio(String nomAlbergueIntercambio) {
        this.nomAlbergueIntercambio = nomAlbergueIntercambio;
    }

    public String getFechaValidaProductoIntercambio() {
        return fechaValidaProductoIntercambio;
    }

    public void setFechaValidaProductoIntercambio(String fechaValidaProductoIntercambio) {
        this.fechaValidaProductoIntercambio = fechaValidaProductoIntercambio;
    }

    public String getImgProductoIntercambio() {
        return imgProductoIntercambio;
    }

    public void setImgProductoIntercambio(String imgProductoIntercambio) {
        this.imgProductoIntercambio = imgProductoIntercambio;
    }
}
