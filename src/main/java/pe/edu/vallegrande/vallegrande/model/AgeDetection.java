package pe.edu.vallegrande.vallegrande.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "age_detection")
public class AgeDetection {

    @Id
    private Long id;

    @Column("image_url")
    private String imageUrl;

    @Column("age")
    private int age;

    @Column("creation_date")
    private LocalDateTime creationDate;

    @Column("update_date")
    private LocalDateTime updateDate;
}
