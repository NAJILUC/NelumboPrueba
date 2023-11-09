package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Historico;
import com.nelumbo.parqueadero.domain.Parqueadero;
import com.nelumbo.parqueadero.domain.ParqueaderoVehiculo;
import com.nelumbo.parqueadero.domain.Vehiculo;
import com.nelumbo.parqueadero.dto.request.EntradaVehiculoRequest;
import com.nelumbo.parqueadero.dto.request.SalidaRequest;
import com.nelumbo.parqueadero.dto.response.IndicadorVehiculoResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoCoincidenciaResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import com.nelumbo.parqueadero.exception.CampoInsuficienteException;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.exception.ObjetoNoExisteException;
import com.nelumbo.parqueadero.feignClient.MicroservicioMensajeService;
import com.nelumbo.parqueadero.feignClient.dto.request.MensajeRequest;
import com.nelumbo.parqueadero.repository.ParqueaderoVehiculoRepository;
import com.nelumbo.parqueadero.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParqueaderoVehiculoServiceImpl implements ParqueaderoVehiculoService {

    @Autowired
    private ParqueaderoVehiculoRepository parqueaderoVehiculoRepository;

    @Autowired
    private ParqueaderoService parqueaderoService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private MicroservicioMensajeService microservicioMensajeService;

    @Autowired
    private IToken iToken;


    @Override
    public List<VehiculoResponse> vehiculosEnParqueadero(Long id) {
        if (parqueaderoService.obtenerParqueadero(id) == null)
            throw new ObjetoNoExisteException("El parqueadero con el id " + id + " no existe");
        List<ParqueaderoVehiculo> parqueaderoVehiculos = parqueaderoVehiculoRepository.findAllByParqueaderoId(id);
        if (parqueaderoVehiculos.isEmpty())
            throw new ObjetoNoExisteException("El parqueadero no contiene vehiculos parqueados");
        return parqueaderoVehiculos.stream()
                .map(parqueaderoVehiculo -> VehiculoResponse.builder()
                        .id(parqueaderoVehiculo.getId())
                        .placa(parqueaderoVehiculo.getVehiculo().getPlaca())
                        .horaIngreso(parqueaderoVehiculo.getHoraIngreso())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long entradaVehiculo(EntradaVehiculoRequest entradaVehiculoRequest) {
            Parqueadero parqueadero = parqueaderoService.obtenerParqueadero(entradaVehiculoRequest.getIdParqueadero());
        if (parqueadero == null)
            throw new ObjetoNoExisteException("El parqueadeo con el id " + entradaVehiculoRequest.getIdParqueadero()
                    + " no existe");
        System.out.println(parqueadero.getUsuario().getId() + " " + iToken.getUserAuthenticatedId(iToken.getBearerToken()));
        if (parqueadero.getUsuario().getId() == iToken.getUserAuthenticatedId(iToken.getBearerToken())) {
            Long vehiculosMax = parqueadero.getVehiculosMaximos();
            Long vehiculosDentro = parqueaderoVehiculoRepository.countAllByParqueaderoId(parqueadero.getId());
            if (vehiculosMax - vehiculosDentro <= 0) {
                throw new CampoInsuficienteException("No hay campo suficiente en el parqueadero");
            }
            Vehiculo vehiculoGuardado;
            if (!vehiculoService.existeVehiculo(entradaVehiculoRequest.getPlaca()))
                vehiculoGuardado = vehiculoService.agregarVehiculo(entradaVehiculoRequest.getPlaca());
            else vehiculoGuardado = vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca());
            ParqueaderoVehiculo parqueaderoVehiculo = ParqueaderoVehiculo.builder()
                    .vehiculo(vehiculoGuardado)
                    .parqueadero(parqueadero)
                    .horaIngreso(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .build();
            try {
                parqueaderoVehiculoRepository.save(parqueaderoVehiculo);
                try {
                    microservicioMensajeService.enviarMensaje(MensajeRequest.builder()
                            .placa(vehiculoGuardado.getPlaca())
                            .mensaje("Vehiculo guardado")
                            .parqueaderoNombre(parqueadero.getNombre())
                            .email(parqueadero.getUsuario().getCorreo())
                            .build());
                } catch (Exception ignore) {
                }
                return vehiculoGuardado.getId();
            } catch (DataIntegrityViolationException e) {
                throw new ObjetoDuplicadoException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
            }
        }
        throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
    }

    @Override
    @Transactional
    public void salidaVehiculo(SalidaRequest salidaRequest) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculo(salidaRequest.getPlaca());
        if (vehiculo == null) {
            throw new ObjetoNoExisteException("No se puede Registrar Salida, no existe la placa en algun parqueadero");
        }
            ParqueaderoVehiculo parqueaderoVehiculo = parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId())
                    .orElseThrow(() -> new ObjetoNoExisteException("No exite el vehiculo con placa " + salidaRequest.getPlaca() + " en algun parqueadero"));
        if (!Objects.equals(parqueaderoVehiculo.getParqueadero().getUsuario().getId(), iToken.getUserAuthenticatedId(iToken.getBearerToken()))) {
            throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
        }
        parqueaderoVehiculoRepository.deleteById(parqueaderoVehiculo.getId());
        Historico historico = Historico.builder()
                .idParqueadero(parqueaderoVehiculo.getParqueadero().getId())
                .nombreParqueadero(parqueaderoVehiculo.getParqueadero().getNombre())
                .horaEnrada(parqueaderoVehiculo.getHoraIngreso())
                .placa(vehiculo.getPlaca())
                .horaSalida(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .pago(Duration.between(parqueaderoVehiculo.getHoraIngreso().toInstant(), Instant.now()).toHours() *
                        parqueaderoVehiculo.getParqueadero().getCosto())
                .build();
        historicoService.agregarHistorico(historico);

    }

    @Override
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes() {
        List<IndicadorVehiculoResponse> vehiculos = new ArrayList<>();
        List<Object[]> vehiculosFrecuentes = historicoService.vehiculosFrecuentes();
        for (Object[] row : vehiculosFrecuentes) {
            vehiculos.add(IndicadorVehiculoResponse.builder()
                    .vehiculo(String.valueOf(row[0]))
                    .visitas(((BigInteger) row[1]).longValue())
                    .build());
        }
        if (vehiculos.isEmpty()) throw new ObjetoNoExisteException("No existen vehiculos frecuentes");
        return vehiculos;
    }

    @Override
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes(Long idParqueadero) {
        if (parqueaderoService.obtenerParqueadero(idParqueadero) == null)
            throw new ObjetoNoExisteException("El parqueadero con el id " + idParqueadero + " no existe");
        List<IndicadorVehiculoResponse> vehiculos = new ArrayList<>();
        List<Object[]> vehiculosFrecuentes = historicoService.vehiculosFrecuentes(idParqueadero);
        for (Object[] row : vehiculosFrecuentes) {
            vehiculos.add(IndicadorVehiculoResponse.builder()
                    .vehiculo(String.valueOf(row[0]))
                    .visitas(((BigInteger) row[1]).longValue())
                    .build());
        }
        return vehiculos;
    }

    @Override
    public List<VehiculoResponse> vehiculosNuevos(Long idParqueadero) {
        if (parqueaderoService.obtenerParqueadero(idParqueadero) == null)
            throw new ObjetoNoExisteException("El parqueadero con el id " + idParqueadero + " no existe");
        List<VehiculoResponse> vehiculosParqueados = vehiculosEnParqueadero(idParqueadero);
        if (vehiculosParqueados.isEmpty())
            throw new ObjetoNoExisteException("El parqueadero con id '" + idParqueadero + "' no contiene vehiculos nuevos");
        List<String> vehiculosViejos = historicoService.historico(idParqueadero);
        List<VehiculoResponse> vehiculosNuevos = new ArrayList<>();
        for (VehiculoResponse vehiculo : vehiculosParqueados) {
            if (!vehiculosViejos.contains(vehiculo.getPlaca())) vehiculosNuevos.add(vehiculo);
        }
        if (vehiculosNuevos.isEmpty())
            throw new ObjetoNoExisteException("El parqueadero con id '" + idParqueadero + "' no contiene vehiculos nuevos");
        return vehiculosNuevos;
    }

    @Override
    public List<VehiculoCoincidenciaResponse> vehiculosCoindicencia(String cadena) {
        List<ParqueaderoVehiculo> idVehiculos = parqueaderoVehiculoRepository.findAll();
        if (idVehiculos.isEmpty()) throw new ObjetoNoExisteException("No hay vehiculos en ningun parqueadero");
        List<VehiculoCoincidenciaResponse> coincidencias = new ArrayList<>();
        for (ParqueaderoVehiculo vehiculo : idVehiculos) {
            if (vehiculo.getVehiculo().getPlaca().contains(cadena.toUpperCase())) {
                coincidencias.add(VehiculoCoincidenciaResponse.builder()
                        .idParqueadero(vehiculo.getParqueadero().getId())
                        .id(vehiculo.getVehiculo().getId())
                        .placa(vehiculo.getVehiculo().getPlaca())
                        .horaIngreso(vehiculo.getHoraIngreso())
                        .build());
            }
        }
        if (coincidencias.isEmpty())
            throw new ObjetoNoExisteException("No se encontraron placas que coincidan con '" + cadena + "'");
        return coincidencias;
    }
}
