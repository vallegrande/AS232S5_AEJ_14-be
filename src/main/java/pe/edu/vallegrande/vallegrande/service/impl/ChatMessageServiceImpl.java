package pe.edu.vallegrande.vallegrande.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vallegrande.model.ChatMessage;
import pe.edu.vallegrande.vallegrande.repository.ChatMessageRepository;
import pe.edu.vallegrande.vallegrande.service.ChatMessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository repository;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<ChatMessage> findAll() {
        log.info("Mostrando todos los mensajes de chat");
        return repository.findAll();
    }

    @Override
    public Mono<ChatMessage> findById(Long id) {
        log.info("Buscando mensaje por ID={}", id);
        return repository.findById(id);
    }

    @Override
    public Mono<ChatMessage> save(ChatMessage message) {
        log.info("Guardando nuevo mensaje en la sesión {}", message.getSessionId());
        message.setCreationDate(LocalDateTime.now()); // aunque la BD lo genera por defecto
        return repository.save(message);
    }

    @Override
    public Mono<ChatMessage> update(Long id, ChatMessage message) {
        log.info("Actualizando mensaje con ID={}", id);
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setUserMessage(message.getUserMessage());
                    existing.setBotMessage(message.getBotMessage());
                    // creationDate no se toca
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Eliminando mensaje con ID={}", id);
        return repository.deleteById(id);
    }
}
