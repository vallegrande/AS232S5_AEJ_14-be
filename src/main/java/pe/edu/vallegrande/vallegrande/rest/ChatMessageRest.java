package pe.edu.vallegrande.vallegrande.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vallegrande.model.ChatMessage;
import pe.edu.vallegrande.vallegrande.service.ChatMessageService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 🔹 Permite que cualquier frontend acceda
public class ChatMessageRest {

    private final ChatMessageService service;

    // 🔹 Listar todas (devuelve array JSON)
    @GetMapping
    public Mono<List<ChatMessage>> getAll() {
        return service.findAll().collectList();
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ChatMessage>> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Guardar mensaje con respuesta automática del bot
    @PostMapping("/save")
    public Mono<ChatMessage> save(@RequestBody ChatMessage message) {
        // 🔹 Generar session_id si es null
        if (message.getSessionId() == null || message.getSessionId().isEmpty()) {
            message.setSessionId(UUID.randomUUID().toString());
        }

        // 🔹 Generar respuesta automática del bot
        String botReply = generarRespuestaBot(message.getUserMessage());
        message.setBotMessage(botReply);

        return service.save(message);
    }

    // 🔹 Actualizar mensaje
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ChatMessage>> update(@PathVariable Long id, @RequestBody ChatMessage message) {
        return service.update(id, message)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Eliminar mensaje
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // 🔹 Respuesta automática del bot
    private String generarRespuestaBot(String userMessage) {
        userMessage = userMessage.toLowerCase();

        if(userMessage.contains("hola")) return "¡Hola! 😊";
        if(userMessage.contains("cómo estás") || userMessage.contains("como estas")) return "Estoy bien, gracias 😄";
        if(userMessage.contains("adiós") || userMessage.contains("chao")) return "¡Hasta luego! 👋";
        if(userMessage.contains("gracias")) return "¡De nada! 😎";

        try {
            String expr = userMessage.replaceAll("[^0-9+\\-*/]", "");
            if(!expr.isEmpty() && expr.matches("[0-9]+[+\\-*/][0-9]+")) {
                String[] parts;
                if(expr.contains("+")) {
                    parts = expr.split("\\+");
                    return "El resultado es: " + (Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]));
                } else if(expr.contains("-")) {
                    parts = expr.split("-");
                    return "El resultado es: " + (Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]));
                } else if(expr.contains("*")) {
                    parts = expr.split("\\*");
                    return "El resultado es: " + (Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]));
                } else if(expr.contains("/")) {
                    parts = expr.split("/");
                    return "El resultado es: " + (Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]));
                }
            }
        } catch(Exception e) {
            return "No entendí la operación 😅";
        }

        return "Hmm… interesante pregunta 🤔";
    }
}
