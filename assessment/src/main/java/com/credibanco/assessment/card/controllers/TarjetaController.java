package com.credibanco.assessment.card.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.credibanco.assessment.card.dto.ResponseDto;
import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.models.TarjetaModel;
import com.credibanco.assessment.card.services.TarjetaService;

@RestController
@RequestMapping("tarjeta")
public class TarjetaController {

    @Autowired
    TarjetaService tarjetaService;

    @GetMapping("consultarTarjeta")
    public TarjetaDto consultarTarjeta(@RequestParam("pan") String pan){
        TarjetaDto tarjeta = new TarjetaDto();
        TarjetaModel tarjetaModel = new TarjetaModel();

        tarjetaModel = tarjetaService.consultarTarjetaPorPan(pan);
        tarjeta.setPan(enmascararPAN(tarjetaModel.getPan()));
        tarjeta.setTitular(tarjetaModel.getTitular());
        tarjeta.setCedula(tarjetaModel.getCedula());
        //tarjeta.setTipo(tarjetaModel.getTipo());
        tarjeta.setTelefono(tarjetaModel.getTelefono());
        tarjeta.setEstado(tarjetaModel.getEstado());
        return tarjeta;
    }

    @PostMapping
    public ResponseDto crearTarjeta(@RequestBody TarjetaDto tarjeta){

        ResponseDto response = new ResponseDto();

        try {
            // validar los parámetros de entrada
            if (tarjeta.getPan() == null || tarjeta.getPan().length() < 16 || tarjeta.getPan().length() > 19) {
                throw new IllegalArgumentException("PAN Inválido");
            }
            if (tarjeta.getTitular() == null || tarjeta.getTitular().isEmpty() || tarjeta.getTitular().length() > 200) {
                throw new IllegalArgumentException("Titular inválido");
            }
            if (tarjeta.getCedula() == null || tarjeta.getCedula().length() < 10 || tarjeta.getCedula().length() > 15) {
                throw new IllegalArgumentException("Cédula inválida");
            }
            if (!"credito".equalsIgnoreCase(tarjeta.getTipo()) && !"debito".equalsIgnoreCase(tarjeta.getTipo())) {
                throw new IllegalArgumentException("Tipo de tarjeta no válido");
            }
            if (tarjeta.getTelefono() == null || String.valueOf(tarjeta.getTelefono()).length() != 10) {
                throw new IllegalArgumentException("Teléfono inválido");
            }

            TarjetaModel tarjetaExistente = new TarjetaModel();
            tarjetaExistente = this.tarjetaService.consultarTarjetaPorPanYCedula(tarjeta.getPan(),tarjeta.getCedula());
            if (tarjetaExistente != null) {
                throw new RuntimeException("La tarjeta ya existe");
                
            }
            
            // número de validación 
            int numeroValidacion = (int) (Math.random() * 100);
           
            // crear la tarjeta
            
            TarjetaModel tarjetaModel = new TarjetaModel();
            tarjetaModel.setPan(tarjeta.getPan());
            tarjetaModel.setTitular(tarjeta.getTitular());
            tarjetaModel.setCedula(tarjeta.getCedula());
            tarjetaModel.setTipo(tarjeta.getTipo());
            tarjetaModel.setTelefono(tarjeta.getTelefono());
            tarjetaModel.setEstado("creada");
            tarjetaModel.setNumeroValidacion(numeroValidacion);

            this.tarjetaService.guardarTarjeta(tarjetaModel);
            
            //PAN enmascarado
            String panEnmascarado = enmascararPAN(String.valueOf(tarjeta.getPan()));

            // respuesta exitosa

            response.setCodigoRespuesta("00");
            response.setMensajeRespuesta("éxito");
            response.setNumeroValidacion(String.valueOf(numeroValidacion));
            response.setPan(panEnmascarado);

            
        } catch (Exception e) {
            // respuesta de error
            response.setCodigoRespuesta("01");
            response.setMensajeRespuesta("fallido");
        }

        return response;

    }

    private String enmascararPAN(String pan) {
        String primeros6 = pan.substring(0, 6);
        String ultimos4 = pan.substring(pan.length() - 4);
        String relleno = "****";
        return primeros6 + relleno + ultimos4;
    }


    @PostMapping("/enrolar")
    public ResponseDto enrolarTarjeta(@RequestBody TarjetaDto tarjeta) {
        ResponseDto response = new ResponseDto();
        try {
            // buscar la tarjeta por PAN y numeroValidacion
            TarjetaModel tarjetaExistente = new TarjetaModel();
            tarjetaExistente = this.tarjetaService.consultarTarjetaPorPan(tarjeta.getPan());
            if (tarjetaExistente == null) {
                response.setCodigoRespuesta("01");
                response.setMensajeRespuesta("Tarjeta no existe");
                throw new RuntimeException("La tarjeta no existe");
                
            }
            tarjetaExistente = this.tarjetaService.consultarTarjetaPorPanYNumeroValidacion(tarjeta.getPan(), tarjeta.getNumeroValidacion());
            if (tarjetaExistente == null) {
                response.setCodigoRespuesta("02");
                response.setMensajeRespuesta("Número de validación inválido");
                throw new RuntimeException("Número de validación inválido");
                
            }
            // cambiar el estado de la tarjeta a enrolada
            tarjetaExistente.setEstado("enrolada");
            this.tarjetaService.guardarTarjeta(tarjetaExistente);

            // respuesta exitosa
            response.setCodigoRespuesta("00");
            response.setMensajeRespuesta("éxito");
        } catch (Exception e) {
        }
        return response;
    }

    @DeleteMapping( path = "/{pan}")
    public ResponseDto eliminarTarjeta(@PathVariable("pan") String pan){
        ResponseDto response = new ResponseDto();
        try {
            TarjetaModel tarjetaExistente = new TarjetaModel();
            tarjetaExistente = this.tarjetaService.consultarTarjetaPorPan(pan);
    
            if (tarjetaExistente == null) {
                throw new RuntimeException("La tarjeta no existe");
            }
    
            boolean result = this.tarjetaService.eliminarTarjeta(tarjetaExistente);
    
            if (result){
                response.setCodigoRespuesta("00");
                response.setMensajeRespuesta("Se ha eliminado la tarjeta");
            }else{
                throw new RuntimeException("Error al eliminar la tarjeta");
            }
        } catch (Exception e) {
            response.setCodigoRespuesta("01");
            response.setMensajeRespuesta("No se ha eliminado la tarjeta");
        }
        return response;
    }




    
}
