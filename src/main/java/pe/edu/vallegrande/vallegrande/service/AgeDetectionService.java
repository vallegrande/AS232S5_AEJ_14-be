package pe.edu.vallegrande.vallegrande.service;

import pe.edu.vallegrande.vallegrande.model.AgeDetection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgeDetectionService {

    // Listar todos los registros
    Flux<AgeDetection> findAll();

    // Buscar por ID
    Mono<AgeDetection> findById(Long id);

    // Guardar un nuevo registro
    Mono<AgeDetection> save(AgeDetection detection);

    // Actualizar un registro existente
    Mono<AgeDetection> update(Long id, AgeDetection detection);

    // Eliminar un registro por ID
    Mono<Void> delete(Long id);
}
