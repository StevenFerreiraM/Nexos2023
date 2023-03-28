package com.credibanco.assessment.card.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credibanco.assessment.card.models.TarjetaModel;
import com.credibanco.assessment.card.repositories.TarjetaRepository;

@Service
public class TarjetaService {
    @Autowired
    TarjetaRepository tarjetaRepository;

    public TarjetaModel consultarTarjetaPorPan(String pan){
        return tarjetaRepository.findByPan(pan);
    }

    public TarjetaModel consultarTarjetaPorPanYNumeroValidacion(String pan, Integer numeroValidacion){
        return tarjetaRepository.findByPanAndNumeroValidacion(pan, numeroValidacion);
    }
    public TarjetaModel consultarTarjetaPorPanYCedula(String pan, String cedula){
        return tarjetaRepository.findByPanAndCedula(pan, cedula);
    }

    public TarjetaModel consultarTarjetaPorPanYEstado(String pan, String estado){
        return tarjetaRepository.findByPanAndEstado(pan, estado);
    }

    public TarjetaModel guardarTarjeta (TarjetaModel tarjeta){
        return tarjetaRepository.save(tarjeta);
    }

    public boolean eliminarTarjeta(TarjetaModel tarjeta) {
        try {
            tarjetaRepository.delete(tarjeta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    


}
