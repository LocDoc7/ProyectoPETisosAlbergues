package com.ay.proyectopetisos.Model;

public class CategoriaCanes {
    private int idCategoria;
    private String descCategoria;
    private String rangoCategoria;
    private int cantidadCanesCategoria;
    private String imgCategoria;

    public CategoriaCanes() {
        this.idCategoria = idCategoria;
        this.descCategoria = descCategoria;
        this.rangoCategoria = rangoCategoria;
        this.cantidadCanesCategoria = cantidadCanesCategoria;
        this.imgCategoria = imgCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    public String getRangoCategoria() {
        return rangoCategoria;
    }

    public void setRangoCategoria(String rangoCategoria) {
        this.rangoCategoria = rangoCategoria;
    }

    public int getCantidadCanesCategoria() {
        return cantidadCanesCategoria;
    }

    public void setCantidadCanesCategoria(int cantidadCanesCategoria) {
        this.cantidadCanesCategoria = cantidadCanesCategoria;
    }

    public String getImgCategoria() {
        return imgCategoria;
    }

    public void setImgCategoria(String imgCategoria) {
        this.imgCategoria = imgCategoria;
    }
}
