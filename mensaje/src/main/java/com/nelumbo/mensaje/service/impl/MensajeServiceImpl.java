package com.nelumbo.mensaje.service.impl;

import com.nelumbo.mensaje.dto.request.MensajeRequest;
import com.nelumbo.mensaje.dto.response.MensajeResponse;
import com.nelumbo.mensaje.service.MensajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;

@Service
public class MensajeServiceImpl implements MensajeService {

    private static final Logger logger = LoggerFactory.getLogger(MensajeServiceImpl.class);

    @Override
    public MensajeResponse enviarMensaje(MensajeRequest mensajeRequest) {
        logger.info(mensajeRequest.toString());
        return MensajeResponse.builder()
                .email(mensajeRequest.getEmail())
                .mensaje(mensajeRequest.getMensaje())
                .parqueaderoNombre(mensajeRequest.getParqueaderoNombre())
                .placa(mensajeRequest.getPlaca())
                .build();
    }
}
