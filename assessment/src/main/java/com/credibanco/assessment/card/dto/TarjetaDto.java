package com.credibanco.assessment.card.dto;

public class TarjetaDto {

    private String pan;    
    private String titular;
    private String cedula;
    private String tipo;
    private Long telefono;
    private String estado;
    private Integer numeroValidacion;


    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Long getTelefono() {
        return telefono;
    }
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getNumeroValidacion() {
        return numeroValidacion;
    }
    public void setNumeroValidacion(Integer numeroValidacion) {
        this.numeroValidacion = numeroValidacion;
    }

    
}
