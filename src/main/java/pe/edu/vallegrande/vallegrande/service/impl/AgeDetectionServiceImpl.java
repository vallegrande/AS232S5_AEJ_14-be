package pe.edu.vallegrande.vallegrande.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vallegrande.model.AgeDetection;
import pe.edu.vallegrande.vallegrande.repository.AgeDetectionRepository;
import pe.edu.vallegrande.vallegrande.service.AgeDetectionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AgeDetectionServiceImpl implements AgeDetectionService {

    private final AgeDetectionRepository repository;

    @Autowired
    public AgeDetectionServiceImpl(AgeDetectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<AgeDetection> findAll() {
        log.info("Mostrando todos los registros de detección de edad");
        return repository.findAll();
    }

    @Override
    public Mono<AgeDetection> findById(Long id) {
        log.info("Buscando registro por ID={}", id);
        return repository.findById(id);
    }

    @Override
    public Mono<AgeDetection> save(AgeDetection detection) {
        log.info("Guardando nueva detección de edad para la imagen {}", detection.getImageUrl());
        detection.setCreationDate(LocalDateTime.now()); // Aunque la BD lo genera por defecto
        detection.setUpdateDate(LocalDateTime.now());
        return repository.save(detection);
    }

    @Override
    public Mono<AgeDetection> update(Long id, AgeDetection detection) {
        log.info("Actualizando detección de edad con ID={}", id);
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setImageUrl(detection.getImageUrl());
                    existing.setAge(detection.getAge());
                    existing.setUpdateDate(LocalDateTime.now());
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Eliminando detección de edad con ID={}", id);
        return repository.deleteById(id);
    }
}
