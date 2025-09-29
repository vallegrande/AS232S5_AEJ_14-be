package pe.edu.vallegrande.vallegrande.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vallegrande.model.ChatMessage;
import pe.edu.vallegrande.vallegrande.service.ChatMessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageRest {

    private final ChatMessageService service;

    // 🔹 Listar todas
    @GetMapping
    public Flux<ChatMessage> getAll() {
        return service.findAll();
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
        // Genera la respuesta automática del bot
        String botReply = generarRespuestaBot(message.getUserMessage());
        message.setBotMessage(botReply);

        // Guarda el mensaje en la base de datos
        return service.save(message);
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ChatMessage>> update(@PathVariable Long id, @RequestBody ChatMessage message) {
        return service.update(id, message)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 🔹 Eliminar
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // 🔹 Método privado que simula la respuesta del bot con lógica simple
    private String generarRespuestaBot(String userMessage) {
    userMessage = userMessage.toLowerCase();

    // Respuestas automáticas simples
    if(userMessage.contains("hola")) return "¡Hola! 😊";
    if(userMessage.contains("cómo estás") || userMessage.contains("como estas")) return "Estoy bien, gracias 😄";
    if(userMessage.contains("adiós") || userMessage.contains("chao")) return "¡Hasta luego! 👋";
    if(userMessage.contains("gracias")) return "¡De nada! 😎";

    // Detectar operaciones matemáticas simples (ejemplo: "10 + 20")
    try {
        String expr = userMessage.replaceAll("[^0-9+\\-*/]", ""); // extrae números y operadores
        if(!expr.isEmpty() && expr.matches("[0-9]+[+\\-*/][0-9]+")) {
            String[] parts;
            if(expr.contains("+")) {
                parts = expr.split("\\+");
                int result = Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                return "El resultado es: " + result;
            } else if(expr.contains("-")) {
                parts = expr.split("-");
                int result = Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]);
                return "El resultado es: " + result;
            } else if(expr.contains("*")) {
                parts = expr.split("\\*");
                int result = Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
                return "El resultado es: " + result;
            } else if(expr.contains("/")) {
                parts = expr.split("/");
                int result = Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]);
                return "El resultado es: " + result;
            }
        }
    } catch(Exception e) {
        return "No entendí la operación 😅";
    }

    // Respuesta por defecto para cualquier otra pregunta
    return "Hmm… interesante pregunta 🤔";
}
}
