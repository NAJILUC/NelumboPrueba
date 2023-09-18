package com.nelumbo.parqueadero.service.impl;

import com.nelumbo.parqueadero.domain.Historico;
import com.nelumbo.parqueadero.domain.ParqueaderoVehiculo;
import com.nelumbo.parqueadero.domain.Vehiculo;
import com.nelumbo.parqueadero.dto.request.EntradaVehiculoRequest;
import com.nelumbo.parqueadero.dto.request.SalidaRequest;
import com.nelumbo.parqueadero.dto.response.IndicadorVehiculoResponse;
import com.nelumbo.parqueadero.dto.response.VehiculoResponse;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.exception.ObjetoDuplicadoException;
import com.nelumbo.parqueadero.exception.VehiculoExisteException;
import com.nelumbo.parqueadero.feignClient.MicroservicioMensajeService;
import com.nelumbo.parqueadero.feignClient.dto.request.MensajeRequest;
import com.nelumbo.parqueadero.repository.ParqueaderoVehiculoRepository;
import com.nelumbo.parqueadero.service.HistoricoService;
import com.nelumbo.parqueadero.service.ParqueaderoService;
import com.nelumbo.parqueadero.service.ParqueaderoVehiculoService;
import com.nelumbo.parqueadero.service.VehiculoService;
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


    @Override
    public List<VehiculoResponse> vehiculosEnParqueadero(Long id) {
        List<ParqueaderoVehiculo> parqueaderoVehiculos = parqueaderoVehiculoRepository.findAll();
        List<VehiculoResponse> vehiculoResponses = new ArrayList<>();
        for (ParqueaderoVehiculo parqueaderoVehiculo : parqueaderoVehiculos) {
            if (parqueaderoVehiculo.getParqueadero().getId()==id)vehiculoResponses.add(
                    VehiculoResponse.builder()
                            .id(parqueaderoVehiculo.getId())
                            .placa(parqueaderoVehiculo.getVehiculo().getPlaca())
                            .horaIngreso(parqueaderoVehiculo.getHoraIngreso())
                            .build());
        }
        return vehiculoResponses;
    }

    @Override
    @Transactional
    public Long entradaVehiculo(EntradaVehiculoRequest entradaVehiculoRequest) {
        if(parqueaderoService.parqueaderoValido(entradaVehiculoRequest.getIdSocio(), entradaVehiculoRequest.getIdParqueadero())){
            Long vehiculosMax = parqueaderoService.verParqueadero(entradaVehiculoRequest.getIdParqueadero()).getVehiculosMaximos();
            Long vehiculosDentro = Long.valueOf(vehiculosEnParqueadero(entradaVehiculoRequest.getIdParqueadero()).size());
            if(vehiculosMax-vehiculosDentro>0 && !vehiculoService.existeVehiculo(entradaVehiculoRequest.getPlaca())){
                vehiculoService.agregarVehiculo(entradaVehiculoRequest.getPlaca());
                ParqueaderoVehiculo parqueaderoVehiculo = ParqueaderoVehiculo.builder()
                        .vehiculo(vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()))
                        .parqueadero(parqueaderoService.obtenerParqueadero(entradaVehiculoRequest.getIdParqueadero()))
                        .horaIngreso(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                        .build();
                try {
                    vehiculoService.agregarVehiculo(entradaVehiculoRequest.getPlaca());
                    parqueaderoVehiculoRepository.save(parqueaderoVehiculo);
                    microservicioMensajeService.enviarMensaje(MensajeRequest.builder()
                            .placa(vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()).getPlaca())
                            .mensaje("Vehiculo guardado")
                            .parqueaderoNombre(entradaVehiculoRequest.getIdParqueadero().toString())
                            .email(parqueaderoService.obtenerParqueadero(entradaVehiculoRequest.getIdParqueadero()).getUsuario().getCorreo()).build());
                    return vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()).getId();
                } catch (DataIntegrityViolationException e) {
                    throw new ObjetoDuplicadoException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
                }
            }else {
                if(vehiculosMax-vehiculosDentro>0) {
                    vehiculoService.agregarVehiculo(entradaVehiculoRequest.getPlaca());
                    ParqueaderoVehiculo parqueaderoVehiculo = ParqueaderoVehiculo.builder()
                            .vehiculo(vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()))
                            .parqueadero(parqueaderoService.obtenerParqueadero(entradaVehiculoRequest.getIdParqueadero()))
                            .horaIngreso(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                            .build();
                    try {
                        parqueaderoVehiculoRepository.save(parqueaderoVehiculo);microservicioMensajeService.enviarMensaje(MensajeRequest.builder()
                                .placa(vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()).getPlaca())
                                .mensaje("Vehiculo guardado")
                                .parqueaderoNombre(entradaVehiculoRequest.getIdParqueadero().toString())
                                .email(parqueaderoService.obtenerParqueadero(entradaVehiculoRequest.getIdParqueadero()).getUsuario().getCorreo()).build());
                        return vehiculoService.obtenerVehiculo(entradaVehiculoRequest.getPlaca()).getId();
                    } catch (DataIntegrityViolationException e) {
                        throw new ObjetoDuplicadoException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
                    }
                } else throw new ObjetoDuplicadoException("No hay campo en el parqueadero");
            }
        }
        throw new ObjetoDuplicadoException("El ID del socio no corresponde al ID del socio asociado al parqueadero");
    }

    @Override
    @Transactional
    public void salidaVehiculo(SalidaRequest salidaRequest) {
        try {
            vehiculoService.existeVehiculo(salidaRequest.getPlaca());
        } catch (RuntimeException e){
            throw new VehiculoExisteException("No se puede Registrar Salida, no existe la placa en algun parqueadero");
        }
        Vehiculo vehiculo = vehiculoService.obtenerVehiculo(salidaRequest.getPlaca());
        ParqueaderoVehiculo parqueaderoVehiculo = new ParqueaderoVehiculo();
        if(parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId()).get()!=null)
            parqueaderoVehiculo=parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId()).get();
        parqueaderoVehiculoRepository.deleteById(parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId()).get().getId());
        vehiculoService.eliminarVehiculo(vehiculo.getId());
        Historico historico = Historico.builder()
                .idParqueadero(parqueaderoVehiculo.getParqueadero().getId())
                .horaEnrada(parqueaderoVehiculo.getHoraIngreso())
                .placa(vehiculo.getPlaca())
                .horaSalida(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .pago(Duration.between(parqueaderoVehiculo.getHoraIngreso().toInstant(), Instant.now()).toHours()*
                        parqueaderoVehiculo.getParqueadero().getCosto())
                .build();
        historicoService.agregarHistorico(historico);
    }

    @Override
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes() {
        List<IndicadorVehiculoResponse> vehiculos = new ArrayList<>();
        List<Object[]> vehiculosFrecuentes = historicoService.vehiculosFrecuentes();
        for (Object row[]: vehiculosFrecuentes) {
            vehiculos.add(IndicadorVehiculoResponse.builder()
                            .vehiculo(String.valueOf(row[0]))
                            .visitas(((BigInteger)row[1]).longValue())
                            .build());
        }
        return vehiculos;
    }

    @Override
    public List<IndicadorVehiculoResponse> vehiculosFrecuentes(Long idParqueadero) {
        List<IndicadorVehiculoResponse> vehiculos = new ArrayList<>();
        List<Object[]> vehiculosFrecuentes = historicoService.vehiculosFrecuentes(idParqueadero);
        for (Object row[]: vehiculosFrecuentes) {
            vehiculos.add(IndicadorVehiculoResponse.builder()
                    .vehiculo(String.valueOf(row[0]))
                    .visitas(((BigInteger)row[1]).longValue())
                    .build());
        }
        return vehiculos;
    }

    @Override
    public List<VehiculoResponse> vehiculosNuevos(Long idParqueadero) {
        List<VehiculoResponse> vehiculosParqueados = vehiculosEnParqueadero(idParqueadero);
        List<VehiculoResponse> nuevosVehiculos = new ArrayList<>();
        List<IndicadorVehiculoResponse> vehiculosViejos = vehiculosFrecuentes(idParqueadero);
        boolean tmp = false;
        for (VehiculoResponse vehiculo: vehiculosParqueados) {
            tmp = false;
            for (IndicadorVehiculoResponse indicadorVehiculoResponse: vehiculosViejos) {
                if(vehiculo.getPlaca().equalsIgnoreCase(indicadorVehiculoResponse.getVehiculo())) {
                    tmp = true;
                    break;
                }
            }
            if(!tmp) nuevosVehiculos.add(VehiculoResponse.builder()
                        .id(vehiculo.getId())
                        .horaIngreso(vehiculo.getHoraIngreso())
                        .placa(vehiculo.getPlaca())
                        .build());
        }
        return nuevosVehiculos;
    }

    @Override
    public List<VehiculoResponse> vehiculosCoindicencia(String cadena) {
        List<Vehiculo> vehiculos = vehiculoService.coincidenciaDePlaca(cadena);
        List<VehiculoResponse> coincidencias = new ArrayList<>();
        ParqueaderoVehiculo parqueaderoVehiculo = new ParqueaderoVehiculo();
        for (Vehiculo vehiculo: vehiculos) {
            parqueaderoVehiculo = parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId()) != null ?
                    parqueaderoVehiculoRepository.findByVehiculoId(vehiculo.getId()).get() : null;
            coincidencias.add(VehiculoResponse.builder()
                    .id(vehiculo.getId())
                    .placa(vehiculo.getPlaca())
                    .horaIngreso(parqueaderoVehiculo.getHoraIngreso())
                    .build());
        }
        return coincidencias;
    }
}
