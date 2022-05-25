package com.ay.proyectopetisos.Model;

public class Albergues {
    private int idAlbergue;
    private String nombreAlbergue;
    private String cantPerritosAlbergue;
    private String cellAlbergue;
    private String imgAlbergue;
    private String ubiAlbergue;

    public Albergues() {
        this.idAlbergue = idAlbergue;
        this.nombreAlbergue = nombreAlbergue;
        this.cellAlbergue = cellAlbergue;
        this.imgAlbergue = imgAlbergue;
        this.ubiAlbergue = ubiAlbergue;
        this.cantPerritosAlbergue = cantPerritosAlbergue;
    }

    public String getCantPerritosAlbergue() {
        return cantPerritosAlbergue;
    }

    public void setCantPerritosAlbergue(String cantPerritosAlbergue) {
        this.cantPerritosAlbergue = cantPerritosAlbergue;
    }

    public int getIdAlbergue() {
        return idAlbergue;
    }

    public void setIdAlbergue(int idAlbergue) {
        this.idAlbergue = idAlbergue;
    }

    public String getNombreAlbergue() {
        return nombreAlbergue;
    }

    public void setNombreAlbergue(String nombreAlbergue) {
        this.nombreAlbergue = nombreAlbergue;
    }

    public String getCellAlbergue() {
        return cellAlbergue;
    }

    public void setCellAlbergue(String cellAlbergue) {
        this.cellAlbergue = cellAlbergue;
    }

    public String getImgAlbergue() {
        return imgAlbergue;
    }

    public void setImgAlbergue(String imgAlbergue) {
        this.imgAlbergue = imgAlbergue;
    }

    public String getUbiAlbergue() {
        return ubiAlbergue;
    }

    public void setUbiAlbergue(String ubiAlbergue) {
        this.ubiAlbergue = ubiAlbergue;
    }
}
