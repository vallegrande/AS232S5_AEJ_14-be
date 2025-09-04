package pe.edu.vallegrande.prueba.repository;

import pe.edu.vallegrande.prueba.model.AgeDetector;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeDetectorRepository extends ReactiveCrudRepository<AgeDetector, Long> {

}