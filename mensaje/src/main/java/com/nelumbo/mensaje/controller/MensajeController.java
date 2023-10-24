package com.nelumbo.mensaje.controller;

import com.nelumbo.mensaje.domain.Historico;
import com.nelumbo.mensaje.dto.request.MensajeRequest;
import com.nelumbo.mensaje.dto.response.HistoricoResponse;
import com.nelumbo.mensaje.dto.response.MensajeResponse;
import com.nelumbo.mensaje.service.MensajeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/indicadores")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoricoResponse> indicadores(){
        return mensajeService.indicadores();
    }
}
