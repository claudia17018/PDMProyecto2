package com.example.proyectopdm.entidades;

public class ResumenServicioSocial {
    private int idResumen;
    private String carnet;
    private String duiTutor;
    private String fechaAperturaExpediente;
    private String fechaEmisionCertificado;

    public ResumenServicioSocial() {
    }

    public ResumenServicioSocial(int idResumen, String carnet, String duiTutor, String fechaAperturaExpediente, String fechaEmisionCertificado) {
        this.idResumen = idResumen;
        this.carnet = carnet;
        this.duiTutor = duiTutor;
        this.fechaAperturaExpediente = fechaAperturaExpediente;
        this.fechaEmisionCertificado = fechaEmisionCertificado;
    }

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getDuiTutor() {
        return duiTutor;
    }

    public void setDuiTutor(String duiTutor) {
        this.duiTutor = duiTutor;
    }

    public String getFechaAperturaExpediente() {
        return fechaAperturaExpediente;
    }

    public void setFechaAperturaExpediente(String fechaAperturaExpediente) {
        this.fechaAperturaExpediente = fechaAperturaExpediente;
    }

    public String getFechaEmisionCertificado() {
        return fechaEmisionCertificado;
    }

    public void setFechaEmisionCertificado(String fechaEmisionCertificado) {
        this.fechaEmisionCertificado = fechaEmisionCertificado;
    }

    @Override
    public String toString() {
        return "ResumenServicioSocial{" +
                "idResumen=" + idResumen +
                ", carnet='" + carnet + '\'' +
                ", duiTutor='" + duiTutor + '\'' +
                ", fechaAperturaExpediente='" + fechaAperturaExpediente + '\'' +
                ", fechaEmisionCertificado='" + fechaEmisionCertificado + '\'' +
                '}';
    }
}
