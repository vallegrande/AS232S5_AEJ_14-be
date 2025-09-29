package pe.edu.vallegrande.vallegrande.service;

import pe.edu.vallegrande.vallegrande.model.ChatMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatMessageService {

    // Listar todos los registros
    Flux<ChatMessage> findAll();

    // Buscar por ID
    Mono<ChatMessage> findById(Long id);

    // Guardar un nuevo mensaje
    Mono<ChatMessage> save(ChatMessage message);

    // Actualizar un registro existente
    Mono<ChatMessage> update(Long id, ChatMessage message);

    // Eliminar un registro por ID
    Mono<Void> delete(Long id);
}