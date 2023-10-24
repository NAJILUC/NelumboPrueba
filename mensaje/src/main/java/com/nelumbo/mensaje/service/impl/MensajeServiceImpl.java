package com.nelumbo.mensaje.service.impl;

import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import com.nelumbo.mensaje.domain.Historico;
import com.nelumbo.mensaje.dto.request.MensajeRequest;
import com.nelumbo.mensaje.dto.response.HistoricoResponse;
import com.nelumbo.mensaje.dto.response.MensajeResponse;
import com.nelumbo.mensaje.repository.HistoricoRepository;
import com.nelumbo.mensaje.service.MensajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MensajeServiceImpl.class);

    public void guardarDatos(String email, String placa, String mensaje, String nombre) {
        Historico historico = new Historico();
        historico.setEmail(email);
        historico.setPlaca(placa);
        historico.setMensaje(mensaje);
        historico.setNombre(nombre);
        historico.setFechaRegistro(new Date());

        historicoRepository.save(historico);
    }

    @Override
    public MensajeResponse enviarMensaje(MensajeRequest mensajeRequest) {
        logger.info(mensajeRequest.toString());
        guardarDatos(mensajeRequest.getEmail(), mensajeRequest.getPlaca(), mensajeRequest.getMensaje(),
                mensajeRequest.getParqueaderoNombre());
        return MensajeResponse.builder()
                .email(mensajeRequest.getEmail())
                .mensaje(mensajeRequest.getMensaje())
                .parqueaderoNombre(mensajeRequest.getParqueaderoNombre())
                .placa(mensajeRequest.getPlaca())
                .build();
    }

    @Override
    public List<HistoricoResponse> indicadores() {
        AggregateIterable<Document> result = mongoTemplate.getCollection("my_collection").aggregate(
                Arrays.asList(new Document("$group",
                new Document("_id", "$email").append("count", new Document("$sum", 1))),
                        new Document("$sort", new Document("count", -1)), new Document("$limit", 5)) )
        ;
        List emailDataList = new ArrayList();
        for (Document document : result) {
            String email = document.getString("_id");
            int cantidad = document.getInteger("count");
            HistoricoResponse emailData = new HistoricoResponse(email, (long) cantidad);
            emailDataList.add(emailData);
        }
        return emailDataList;

}

}
