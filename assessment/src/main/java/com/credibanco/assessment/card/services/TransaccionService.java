package com.credibanco.assessment.card.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credibanco.assessment.card.models.TransaccionModel;
import com.credibanco.assessment.card.repositories.TransaccionRepository;

@Service
public class TransaccionService {
    @Autowired
    TransaccionRepository TransaccionRepository;


    public TransaccionModel consultarTransaccionPorPanYReferenciaYTotal(String pan, Integer referencia, Long total){
        return TransaccionRepository.findByPanAndReferenciaAndTotal(pan, referencia, total);
    }

    public TransaccionModel guardarTransaccion (TransaccionModel transaccion){
        return TransaccionRepository.save(transaccion);
    }

}
