package pe.edu.vallegrande.vallegrande.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    private Long id;

    @Column("session_id")
    private String sessionId;

    @Column("user_message")
    private String userMessage;

    @Column("bot_message")
    private String botMessage;

    @Column("creation_date")
    private LocalDateTime creationDate;
}
