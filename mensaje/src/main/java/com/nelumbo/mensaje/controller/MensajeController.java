package com.nelumbo.mensaje.controller;

import com.nelumbo.mensaje.dto.request.MensajeRequest;
import com.nelumbo.mensaje.dto.response.MensajeResponse;
import com.nelumbo.mensaje.service.MensajeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/mensaje")
@AllArgsConstructor
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping("/enviar")
    @ResponseStatus(HttpStatus.CREATED)
    public MensajeResponse enviarMensaje(@Valid @RequestBody MensajeRequest mensajeRequest){
        return mensajeService.enviarMensaje(mensajeRequest);
    }
}
