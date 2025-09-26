package pe.edu.vallegrande.imageGenerator.service;

import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageGeneratorService {

    // Listar todos los registros
    Flux<ImageGenerator> findAll();

    // Buscar por ID
    Mono<ImageGenerator> findById(Long id);

    // Guardar sin generar imagen (solo BD)
    Mono<ImageGenerator> save(String prompt, int styleId, String size);

    // Actualizar un registro existente
    Mono<ImageGenerator> update(Long id, String prompt, int styleId, String size);

    // Generar imagen con RapidAPI + guardar en BD
    Mono<ImageGenerator> generateImage(String prompt, int styleId, String size);


    // Eliminar un registro por ID
    Mono<Void> delete(Long id);
}