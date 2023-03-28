package com.credibanco.assessment.card.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.models.*;
import com.credibanco.assessment.card.services.*;

@RestController
@RequestMapping("transaccion")
public class TransaccionControler {

    @Autowired
    TransaccionService transaccionService;
    @Autowired
    TarjetaService tarjetaService;

    @PostMapping("/crear")
    public ResponseDto crearTransaccion (@RequestBody TransaccionDto transaccion){
        ResponseDto response = new ResponseDto();
        TransaccionModel transaccionModel = new TransaccionModel();
        try {            
            TarjetaModel tarjeta = new TarjetaModel();
            
            tarjeta = this.tarjetaService.consultarTarjetaPorPan(transaccion.getPan());
            if (tarjeta == null) {
                response.setCodigoRespuesta("01");
                response.setMensajeRespuesta("Tarjeta no existe");
                response.setEstadoTransacción("Rechazada");
                transaccionModel.setReferencia(transaccion.getReferencia());
                throw new RuntimeException("La tarjeta no existe");            
            }
            tarjeta =  this.tarjetaService.consultarTarjetaPorPanYEstado(transaccion.getPan(), "enrolada");
            if (tarjeta == null) {
                response.setCodigoRespuesta("02");
                response.setMensajeRespuesta("Tarjeta no enrolada");
                response.setEstadoTransacción("Rechazada");
                transaccionModel.setReferencia(transaccion.getReferencia());
                throw new RuntimeException("La tarjeta no esta enrolada");
            }

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            transaccionModel.setPan(transaccion.getPan());
            transaccionModel.setEstado("Aprobada");
            transaccionModel.setReferencia(transaccion.getReferencia());
            transaccionModel.setTotal(transaccion.getTotal());
            transaccionModel.setDireccion(transaccion.getDireccion());
            transaccionModel.setTimestamp(sqlTimestamp);
            
            this.transaccionService.guardarTransaccion(transaccionModel);
            response.setCodigoRespuesta("00");
            response.setMensajeRespuesta("Compra exitosa");
            response.setEstadoTransacción(transaccionModel.getEstado());
            response.setReferencia(transaccionModel.getReferencia());


        } catch (Exception e) {
        }

        return response;

    }

    @PostMapping("/anular")
    public ResponseDto anularTransaccion (@RequestBody TransaccionDto transaccion){
        ResponseDto response = new ResponseDto();
        TransaccionModel transaccionModel = new TransaccionModel();

        try {

            transaccionModel = this.transaccionService.consultarTransaccionPorPanYReferenciaYTotal(transaccion.getPan(), transaccion.getReferencia(), transaccion.getTotal());
            
            if (transaccionModel == null){
                response.setCodigoRespuesta("01");
                response.setMensajeRespuesta("Número de refencia inválido");
                throw new RuntimeException("Número de refencia inválido");
            }
            long now = System.currentTimeMillis();
            Timestamp actualTimestamp = new Timestamp(now);
            long milliseconds = actualTimestamp.getTime() - transaccionModel.getTimestamp().getTime();
            int seconds = (int) milliseconds / 1000;
            int minutes = seconds / 60;

            if (minutes > 5){
                response.setCodigoRespuesta("02");
                response.setMensajeRespuesta("No se puede anular transacción");
                throw new RuntimeException("No se puede anular transacción");
            }

            transaccionModel.setEstado("Anulada");
            this.transaccionService.guardarTransaccion(transaccionModel);
            response.setCodigoRespuesta("00");
            response.setMensajeRespuesta("Compra anulada");
            response.setReferencia(transaccionModel.getReferencia());

        } catch (Exception e) {
        }   

        return response;
    }
    
}
