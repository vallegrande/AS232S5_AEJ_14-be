package pe.edu.vallegrande.prueba.service;

import pe.edu.vallegrande.prueba.model.AgeDetector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgeDetectorService {

    Flux<AgeDetector> findAll();

    Mono<AgeDetector> findById(Long id);

    Mono<AgeDetector> save(String imageUrl);

    Mono<AgeDetector> update(String imageUrl);

    Mono<AgeDetector> ageDetector(String imageUrl);

}