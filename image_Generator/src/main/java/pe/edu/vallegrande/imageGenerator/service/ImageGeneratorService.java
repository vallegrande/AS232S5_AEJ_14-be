package pe.edu.vallegrande.imageGenerator.service;

import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageGeneratorService {

    Flux<ImageGenerator> findAll();

    Mono<ImageGenerator> findById(Long id);

    Mono<ImageGenerator> save(String prompt, int styleId, String size);

    Mono<ImageGenerator> update(Long id, String prompt, int styleId, String size);

    Mono<ImageGenerator> generateImage(String prompt, int styleId, String size);

}
