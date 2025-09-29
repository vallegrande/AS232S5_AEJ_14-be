package pe.edu.vallegrande.vallegrande.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vallegrande.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessage, Long> {

}
