package com.credibanco.assessment.card.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.credibanco.assessment.card.models.TransaccionModel;

@Repository
public interface TransaccionRepository extends CrudRepository<TransaccionModel, Long> {

    TransaccionModel findByPanAndReferenciaAndTotal(String pan, Integer referencia, Long Total);
   
}
