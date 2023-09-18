package com.nelumbo.parqueadero.feignClient;

import com.nelumbo.parqueadero.feignClient.dto.request.MensajeRequest;
import com.nelumbo.parqueadero.feignClient.dto.response.MensajeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "mensaje-service", url = "localhost:8081/api/v1/mensaje")
public interface MicroservicioMensajeService {
    @PostMapping("/enviar")
    public MensajeResponse enviarMensaje(@Valid @RequestBody MensajeRequest mensajeRequest);
}
