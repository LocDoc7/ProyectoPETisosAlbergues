package com.ay.proyectopetisosalbergue.Model;

public class Productos {
    private int id_Albergue;
    private int idProducto;
    private String proNombre;
    private String proDescripcion;
    private float proPrecioReal;
    private float proPrecioCollares;
    private String imgProducto;

    public Productos() {
        this.id_Albergue = id_Albergue;
        this.idProducto = idProducto;
        this.proDescripcion = proDescripcion;
        this.proPrecioReal = proPrecioReal;
        this.proPrecioCollares = proPrecioCollares;
        this.imgProducto = imgProducto;
        this.proNombre = proNombre;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public int getId_Albergue() {
        return id_Albergue;
    }

    public void setId_Albergue(int id_Albergue) {
        this.id_Albergue = id_Albergue;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public float getProPrecioReal() {
        return proPrecioReal;
    }

    public void setProPrecioReal(float proPrecioReal) {
        this.proPrecioReal = proPrecioReal;
    }

    public float getProPrecioCollares() {
        return proPrecioCollares;
    }

    public void setProPrecioCollares(float proPrecioCollares) {
        this.proPrecioCollares = proPrecioCollares;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }
}
