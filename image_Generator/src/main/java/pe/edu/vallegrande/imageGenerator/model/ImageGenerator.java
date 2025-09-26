package pe.edu.vallegrande.imageGenerator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "image_generator")
public class ImageGenerator {

    @Id
    private Long id;

    private String prompt;

    @Column(value = "style_id")
    private int styleId;

    private String size;

    @Column(value = "creation_date")
    private LocalDateTime creationDate;

    @Column(value = "update_date")
    private LocalDateTime updateDate;
}