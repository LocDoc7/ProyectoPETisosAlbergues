package com.ay.proyectopetisos.Model;

public class Notificaciones {
    private  String notTituto;
    private  String notDescripcion;
    private  String notFecha;
    private  String notHora;
    private  String imgAlbergueLogo;

    public Notificaciones() {
        this.notTituto = notTituto;
        this.notDescripcion = notDescripcion;
        this.notFecha = notFecha;
        this.notHora = notHora;
        this.imgAlbergueLogo = imgAlbergueLogo;
    }

    public String getNotTituto() {
        return notTituto;
    }

    public void setNotTituto(String notTituto) {
        this.notTituto = notTituto;
    }

    public String getNotDescripcion() {
        return notDescripcion;
    }

    public void setNotDescripcion(String notDescripcion) {
        this.notDescripcion = notDescripcion;
    }

    public String getNotFecha() {
        return notFecha;
    }

    public void setNotFecha(String notFecha) {
        this.notFecha = notFecha;
    }

    public String getNotHora() {
        return notHora;
    }

    public void setNotHora(String notHora) {
        this.notHora = notHora;
    }

    public String getImgAlbergueLogo() {
        return imgAlbergueLogo;
    }

    public void setImgAlbergueLogo(String imgAlbergueLogo) {
        this.imgAlbergueLogo = imgAlbergueLogo;
    }
}
