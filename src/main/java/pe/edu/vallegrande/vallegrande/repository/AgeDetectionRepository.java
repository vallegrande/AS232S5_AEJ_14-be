package pe.edu.vallegrande.vallegrande.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vallegrande.model.AgeDetection;

@Repository
public interface AgeDetectionRepository extends ReactiveCrudRepository<AgeDetection, Long> {

}
