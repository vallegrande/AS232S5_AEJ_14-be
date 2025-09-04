package pe.edu.vallegrande.prueba.model;

import lombok.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "age_detector")
public class AgeDetector {

    @Id
    private Long id;

    @Column(value = "image_url")
    private String imageUrl;

    private int age;

    @Column(value = "creation_date")
    private LocalDateTime creationDate;

    @Column(value = "update_date")
    private LocalDateTime updateDate;

}