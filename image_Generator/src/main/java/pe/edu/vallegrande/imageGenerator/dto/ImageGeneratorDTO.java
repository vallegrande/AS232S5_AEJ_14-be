package pe.edu.vallegrande.imageGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGeneratorDTO {
    private String prompt;
    private int styleId;
    private String size;
}
