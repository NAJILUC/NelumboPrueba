package com.nelumbo.parqueadero.service;

import com.nelumbo.parqueadero.domain.Historico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoricoService {

    void agregarHistorico(Historico historico);

    public List<Object[]> vehiculosFrecuentes();

    public List<Object[]> vehiculosFrecuentes(Long idParqueadero);

    Double gananciasHoy(Long idParqueadero);

    Double gananciasMes(Long idParqueadero);

    Double gananciasAnio(Long idParqueadero);

    List<String> historico(Long idParqueadero);
}
