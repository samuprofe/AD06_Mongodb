package org.example.mongodb.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {
    private String id;
    private String contenido;
    private LocalDate fechaCreacion;
    private String autorId;
    private String autorNombre;
}
