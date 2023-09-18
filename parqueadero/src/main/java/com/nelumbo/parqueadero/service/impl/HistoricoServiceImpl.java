package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Historico;
import com.nelumbo.parqueadero.exception.ErrorResponseMessage;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.repository.HistoricoRepository;
import com.nelumbo.parqueadero.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class HistoricoServiceImpl implements HistoricoService {

    @Autowired
    HistoricoRepository historicoRepository;

    @Override
    public void agregarHistorico(Historico historico) {
        historicoRepository.save(historico);
    }

    public List<Object[]> vehiculosFrecuentes(){
        return historicoRepository.findTop10VehiculosMasFrecuentes();
    }

    @Override
    public List<Object[]> vehiculosFrecuentes(Long idParqueadero) {
        return historicoRepository.findTop10VehiculosMasFrecuentes(idParqueadero);
    }

    @Override
    public Double gananciasHoy(Long idParqueadero) {
        if(historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero)==null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
    }

    @Override
    public Double gananciasMes(Long idParqueadero) {
        if(historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero)==null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
    }

    @Override
    public Double gananciasAnio(Long idParqueadero) {
        if(historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero)==null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historicoRepository.gananciasHoy(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
    }
}
