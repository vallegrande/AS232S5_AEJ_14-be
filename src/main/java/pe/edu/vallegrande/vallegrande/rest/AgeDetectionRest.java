package pe.edu.vallegrande.vallegrande.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vallegrande.model.AgeDetection;
import pe.edu.vallegrande.vallegrande.service.AgeDetectionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/age-detection")
@RequiredArgsConstructor
public class AgeDetectionRest {

    private final AgeDetectionService service;

    // 🔹 Listar todos los registros
    @GetMapping
    public Flux<AgeDetection> getAll() {
        return service.findAll();
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<AgeDetection>> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Guardar un nuevo registro
    @PostMapping("/save")
    public Mono<AgeDetection> save(@RequestBody AgeDetection detection) {
        detection.setCreationDate(LocalDateTime.now());
        detection.setUpdateDate(LocalDateTime.now());
        return service.save(detection);
    }

    // 🔹 Actualizar un registro existente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<AgeDetection>> update(@PathVariable Long id, @RequestBody AgeDetection detection) {
        detection.setUpdateDate(LocalDateTime.now());
        return service.update(id, detection)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Eliminar un registro por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
