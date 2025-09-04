package pe.edu.vallegrande.prueba.service.impl;

import pe.edu.vallegrande.prueba.model.AgeDetector;
import pe.edu.vallegrande.prueba.service.AgeDetectorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.prueba.repository.AgeDetectorRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgeDetectorServiceImpl implements AgeDetectorService {

    private final AgeDetectorRepository ageDetectorRepository;
    private final WebClient webClient;

    @Autowired
    public AgeDetectorServiceImpl(AgeDetectorRepository ageDetectorRepository, WebClient webClient) {
        this.ageDetectorRepository = ageDetectorRepository;
        this.webClient = webClient;
    }

    @Override
    public Flux<AgeDetector> findAll() {
        log.info("Mostrando todos los datos");
        return ageDetectorRepository.findAll();
    }

    @Override
    public Mono<AgeDetector> findById(Long id) {
        log.info("Buscando por ID = {}", id);
        return ageDetectorRepository.findById(id);
    }

    @Override
    public Mono<AgeDetector> save(String imageUrl) {
        log.info("Guardando nuevo registro con URL = {}", imageUrl);
        return ageDetector(imageUrl);
    }

    @Override
    public Mono<AgeDetector> update(String imageUrl) {
        log.info("Actualizando registro con URL = {}", imageUrl);
        return ageDetector(imageUrl);
    }

    // Record que representa el body enviado a la API externa
    public record AgeRequest(String url) {}

    // Clase interna para mapear la respuesta de RapidAPI
    public static class ApiResponse {
        public List<Integer> age;
        public String gender;
    }

    @Override
    public Mono<AgeDetector> ageDetector(String imageUrl) {
        return webClient.post()
                .uri("/age-detection")
                .bodyValue(new AgeRequest(imageUrl))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMap(response -> {
                    Integer age = 0; // Valor por defecto
                    if (response.age != null && !response.age.isEmpty()) {
                        age = response.age.get(0); // Tomar el primer valor si existe
                    }

                    AgeDetector detector = new AgeDetector();
                    detector.setImageUrl(imageUrl);
                    detector.setAge(age);
                    detector.setCreationDate(LocalDateTime.now());
                    detector.setUpdateDate(LocalDateTime.now());
                    log.info("Registrando en BD: {}", detector);

                    return ageDetectorRepository.save(detector);
                })
                .onErrorResume(e -> {
                    log.error("Error al consumir API de RapidAPI: {}", e.getMessage(), e);
                    // Guardar registro con edad = 0 si falla la API
                    AgeDetector detector = new AgeDetector();
                    detector.setImageUrl(imageUrl);
                    detector.setAge(0);
                    detector.setCreationDate(LocalDateTime.now());
                    detector.setUpdateDate(LocalDateTime.now());
                    return ageDetectorRepository.save(detector);
                });
    }
}
