package com.credibanco.assessment.card.dto;

public class ResponseDto {

    private String codigoRespuesta;
    private String mensajeRespuesta;
    private String numeroValidacion;
    private String pan;
    private String estadoTransacción;
    private Integer referencia;
    
    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }
    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
    public String getNumeroValidacion() {
        return numeroValidacion;
    }
    public void setNumeroValidacion(String numeroValidacion) {
        this.numeroValidacion = numeroValidacion;
    }
    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getEstadoTransacción() {
        return estadoTransacción;
    }
    public void setEstadoTransacción(String estadoTransacción) {
        this.estadoTransacción = estadoTransacción;
    }
    public Integer getReferencia() {
        return referencia;
    }
    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    
}
