package pe.edu.vallegrande.imageGenerator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import pe.edu.vallegrande.imageGenerator.repository.ImageGeneratorRepository;
import pe.edu.vallegrande.imageGenerator.service.ImageGeneratorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ImageGeneratorServiceImpl implements ImageGeneratorService {

    private final ImageGeneratorRepository repository;
    private final WebClient webClient;

    @Autowired
    public ImageGeneratorServiceImpl(ImageGeneratorRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    @Override
    public Flux<ImageGenerator> findAll() {
        log.info("Mostrando todas las solicitudes de generación de imágenes");
        return repository.findAll();
    }

    @Override
    public Mono<ImageGenerator> findById(Long id) {
        log.info("Buscando por ID = {}", id);
        return repository.findById(id);
    }

    @Override
    public Mono<ImageGenerator> save(String prompt, int styleId, String size) {
        log.info("Guardando nueva solicitud de imagen con prompt = {}", prompt);
        return generateImage(prompt, styleId, size);
    }

    @Override
    public Mono<ImageGenerator> update(Long id, String prompt, int styleId, String size) {
        log.info("Actualizando solicitud con ID = {}", id);
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setPrompt(prompt);
                    existing.setStyleId(styleId);
                    existing.setSize(size);
                    existing.setUpdateDate(LocalDateTime.now());
                    return repository.save(existing);
                });
    }

    // Record para request hacia RapidAPI
    public record ImageRequest(String prompt, int style_id, String size) {}

    // Clase interna para mapear la respuesta de RapidAPI
    public static class ApiResponse {
        public String url; // aquí recibes la URL de la imagen generada, pero no la guardamos en DB
    }

    @Override
    public Mono<ImageGenerator> generateImage(String prompt, int styleId, String size) {
        return webClient.post()
                .uri("/generate-image") // <-- Ajusta al endpoint real de RapidAPI
                .bodyValue(new ImageRequest(prompt, styleId, size))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMap(response -> {
                    ImageGenerator entity = new ImageGenerator();
                    entity.setPrompt(prompt);
                    entity.setStyleId(styleId);
                    entity.setSize(size);
                    entity.setCreationDate(LocalDateTime.now());
                    entity.setUpdateDate(LocalDateTime.now());
                    log.info("Imagen generada (URL={}): Guardando en BD {}", response.url, entity);

                    return repository.save(entity);
                })
                .onErrorResume(e -> {
                    log.error("Error al consumir API de RapidAPI: {}", e.getMessage(), e);
                    ImageGenerator entity = new ImageGenerator();
                    entity.setPrompt(prompt);
                    entity.setStyleId(styleId);
                    entity.setSize(size);
                    entity.setCreationDate(LocalDateTime.now());
                    entity.setUpdateDate(LocalDateTime.now());
                    return repository.save(entity);
                });
    }
}
