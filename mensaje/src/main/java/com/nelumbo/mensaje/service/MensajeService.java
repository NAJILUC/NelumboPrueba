package com.nelumbo.mensaje.service;

import com.nelumbo.mensaje.dto.request.MensajeRequest;
import com.nelumbo.mensaje.dto.response.MensajeResponse;
import org.springframework.stereotype.Service;

@Service
public interface MensajeService {

    MensajeResponse enviarMensaje(MensajeRequest  mensajeRequest);
}
