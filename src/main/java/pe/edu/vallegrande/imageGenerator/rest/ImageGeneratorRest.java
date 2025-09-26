package pe.edu.vallegrande.imageGenerator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.imageGenerator.dto.ImageGeneratorDTO;
import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import pe.edu.vallegrande.imageGenerator.service.ImageGeneratorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageGeneratorRest {

    private final ImageGeneratorService service;

    // 🔹 Listar todas
    @GetMapping
    public Flux<ImageGenerator> getAll() {
        return service.findAll();
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ImageGenerator>> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Guardar sin generar imagen
    @PostMapping("/save")
    public Mono<ImageGenerator> save(@RequestBody ImageGeneratorDTO dto) {
        return service.save(dto.getPrompt(), dto.getStyleId(), dto.getSize());
    }

    // 🔹 Generar imagen con API + guardar en BD
    @PostMapping("/generate")
    public Mono<ImageGenerator> generateImage(@RequestBody ImageGeneratorDTO dto) {
        return service.generateImage(dto.getPrompt(), dto.getStyleId(), dto.getSize());
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ImageGenerator>> update(@PathVariable Long id, @RequestBody ImageGeneratorDTO dto) {
        return service.update(id, dto.getPrompt(), dto.getStyleId(), dto.getSize())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Eliminar
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
