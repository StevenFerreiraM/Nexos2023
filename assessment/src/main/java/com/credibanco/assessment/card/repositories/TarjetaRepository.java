package com.credibanco.assessment.card.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.credibanco.assessment.card.models.TarjetaModel;

@Repository
public interface TarjetaRepository extends CrudRepository<TarjetaModel, Long> {

    TarjetaModel findByPan(String pan);
    
    TarjetaModel findByPanAndNumeroValidacion(String pan, Integer numeroValidacion);
    
    TarjetaModel findByPanAndCedula(String pan, String cedula);

    Long deleteByPan(String pan);

    TarjetaModel findByPanAndEstado(String pan, String estado);
    
}
