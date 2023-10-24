package com.nelumbo.mensaje.repository;

import com.nelumbo.mensaje.domain.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoRepository extends MongoRepository<Historico, String> {
}
