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
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ImageGeneratorServiceImpl implements ImageGeneratorService {

    private final ImageGeneratorRepository repository;
    private final WebClient webClient;

    @Autowired
    public ImageGeneratorServiceImpl(ImageGeneratorRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient; // ✅ usa el bean configurado en WebClientConfig
    }

    @Override
    public Flux<ImageGenerator> findAll() {
        log.info("Mostrando todas las solicitudes");
        return repository.findAll();
    }

    @Override
    public Mono<ImageGenerator> findById(Long id) {
        log.info("Buscando por ID={}", id);
        return repository.findById(id);
    }

    @Override
    public Mono<ImageGenerator> save(String prompt, int styleId, String size) {
        log.info("Guardando con prompt={}", prompt);
        return generateImage(prompt, styleId, size);
    }

    @Override
    public Mono<ImageGenerator> update(Long id, String prompt, int styleId, String size) {
        log.info("Actualizando ID={}", id);
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setPrompt(prompt);
                    existing.setStyleId(styleId);
                    existing.setSize(size);
                    existing.setUpdateDate(LocalDateTime.now());
                    return repository.save(existing);
                });
    }

    // ✅ Clase para mapear respuesta de RapidAPI
    public static class ApiResponse {
        public List<Output> output;

        public static class Output {
            public String image; // URL de la imagen generada
        }
    }

    @Override
    public Mono<ImageGenerator> generateImage(String prompt, int styleId, String size) {
        return webClient.post()
                .uri("/aaaaaaaaaaaaaaaaaiimagegenerator/quick.php")
                .bodyValue(Map.of(
                        "prompt", prompt,
                        "style_id", styleId,
                        "size", size
                ))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMap(response -> {
                    ImageGenerator entity = new ImageGenerator();
                    entity.setPrompt(prompt);
                    entity.setStyleId(styleId);
                    entity.setSize(size);

                    // ✅ Usamos la primera imagen del array output
                    if (response.output != null && !response.output.isEmpty()) {
                        entity.setImageUrl(response.output.get(0).image);
                        log.info("Imagen generada: {}", entity.getImageUrl());
                    } else {
                        entity.setImageUrl(null);
                        log.warn("No se recibió imagen desde la API");
                    }

                    entity.setCreationDate(LocalDateTime.now());
                    entity.setUpdateDate(LocalDateTime.now());
                    return repository.save(entity);
                })
                .onErrorResume(e -> {
                    log.error("Error en API: {}", e.getMessage());

                    ImageGenerator entity = new ImageGenerator();
                    entity.setPrompt(prompt);
                    entity.setStyleId(styleId);
                    entity.setSize(size);
                    entity.setImageUrl(null);
                    entity.setCreationDate(LocalDateTime.now());
                    entity.setUpdateDate(LocalDateTime.now());
                    return repository.save(entity);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Eliminando ID={}", id);
        return repository.deleteById(id);
    }
}
