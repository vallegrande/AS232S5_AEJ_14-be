package pe.edu.vallegrande.imageGenerator.rest;

import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import pe.edu.vallegrande.imageGenerator.service.ImageGeneratorService;
import pe.edu.vallegrande.imageGenerator.service.impl.ImageGeneratorServiceImpl.ImageRequest;
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

    // Listar todos los registros
    @GetMapping
    public Flux<ImageGenerator> findAll() {
        return imageGeneratorService.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Mono<ImageGenerator> findById(@PathVariable Long id) {
        return imageGeneratorService.findById(id);
    }

    // Guardar (genera imagen + almacena en BD)
    @PostMapping("/save")
    public Mono<ImageGenerator> save(@RequestBody ImageRequest request) {
        return imageGeneratorService.generateImage(
                request.prompt(),
                request.style_id(),
                request.size()
        );
    }

    // Actualizar un registro
    @PutMapping("/update/{id}")
    public Mono<ImageGenerator> update(@PathVariable Long id, @RequestBody ImageRequest request) {
        return imageGeneratorService.update(
                id,
                request.prompt(),
                request.style_id(),
                request.size()
        );
    }

}
