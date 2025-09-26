package pe.edu.vallegrande.imageGenerator.repository;

import pe.edu.vallegrande.imageGenerator.model.ImageGenerator;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageGeneratorRepository extends ReactiveCrudRepository<ImageGenerator, Long> {

}
