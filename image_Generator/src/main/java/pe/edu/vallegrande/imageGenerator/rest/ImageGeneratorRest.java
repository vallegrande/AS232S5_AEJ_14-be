package pe.edu.vallegrande.imageGenerator.rest;

import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import pe.edu.vallegrande.imageGenerator.service.ImageGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image-generator")
public class ImageGeneratorRest {

    private final ImageGeneratorService imageGeneratorService;

    @Autowired
    public ImageGeneratorRest(ImageGeneratorService imageGeneratorService) {
        this.imageGeneratorService = imageGeneratorService;
    }

    // Listar todos
    @GetMapping
    public Flux<ImageGenerator> findAll() {
        return imageGeneratorService.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Mono<ImageGenerator> findById(@PathVariable Long id) {
        return imageGeneratorService.findById(id);
    }

    // Generar imagen + guardar en BD
    @PostMapping("/generate")
    public Mono<ImageGenerator> generate(@RequestBody ImageGenerator request) {
        return imageGeneratorService.generateImage(
                request.getPrompt(),
                request.getStyleId(),
                request.getSize()
        );
    }

    // Actualizar
    @PutMapping("/update/{id}")
    public Mono<ImageGenerator> update(@PathVariable Long id, @RequestBody ImageGenerator request) {
        return imageGeneratorService.update(
                id,
                request.getPrompt(),
                request.getStyleId(),
                request.getSize()
        );
    }

    // Eliminar
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return imageGeneratorService.delete(id);
    }
}
