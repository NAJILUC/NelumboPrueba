package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Historico;
import com.nelumbo.parqueadero.domain.Parqueadero;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.exception.ObjetoNoExisteException;
import com.nelumbo.parqueadero.repository.HistoricoRepository;
import com.nelumbo.parqueadero.service.HistoricoService;
import com.nelumbo.parqueadero.service.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class HistoricoServiceImpl implements HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;
    @Autowired
    private IToken iToken;

    @Autowired
    private ParqueaderoServiceImpl parqueaderoService;

    @Override
    public void agregarHistorico(Historico historico) {
        historicoRepository.save(historico);
    }

    public List<Object[]> vehiculosFrecuentes() {
        return historicoRepository.findTop10VehiculosMasFrecuentes();
    }

    @Override
    public List<Object[]> vehiculosFrecuentes(Long idParqueadero) {
        if (parqueaderoService.obtenerParqueadero(idParqueadero) == null)
            throw new ObjetoNoExisteException("El parqueadero con id " + idParqueadero + " no existe");
        List<Object[]> vehiculos = historicoRepository.findTop10VehiculosMasFrecuentes(idParqueadero);
        if (vehiculos.isEmpty()) throw new ObjetoNoExisteException("El parqueadero con id " + idParqueadero +
                " no tiene registros existentes");
        return vehiculos;
    }

    @Override
    public Double gananciasHoy(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.obtenerParqueadero(idParqueadero);
        if (parqueadero == null)
            throw new ObjetoNoExisteException("El parqueadero con id " + idParqueadero + " no existe");
        Double historico = historicoRepository.gananciasDesde(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
        if (parqueadero.getUsuario().getId() != iToken.getUserAuthenticatedId(iToken.getBearerToken())) {
            throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
        }
        if (historico == null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historico;
    }

    @Override
    public Double gananciasMes(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.obtenerParqueadero(idParqueadero);
        if (parqueadero == null)
            throw new ObjetoNoExisteException("El parqueadero con id " + idParqueadero + " no existe");
        Double historico = historicoRepository.gananciasDesde(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
        if (parqueadero.getUsuario().getId() != iToken.getUserAuthenticatedId(iToken.getBearerToken())) {
            throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
        }
        if (historico == null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historico;
    }

    @Override
    public Double gananciasAnio(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.obtenerParqueadero(idParqueadero);
        if (parqueadero == null)
            throw new ObjetoNoExisteException("El parqueadero con id " + idParqueadero + " no existe");
        Double historico = historicoRepository.gananciasDesde(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), idParqueadero);
        if (parqueadero.getUsuario().getId() != iToken.getUserAuthenticatedId(iToken.getBearerToken())) {
            throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
        }
        if (historico == null)
            throw new NotFoundException("No hay registros del parqueadero " + idParqueadero);
        return historico;
    }

    @Override
    public List<String> historico(Long idParqueadero) {
        return historicoRepository.historialVehiculos(idParqueadero);
    }
}
