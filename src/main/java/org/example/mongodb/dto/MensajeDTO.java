package org.example.mongodb.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {
    private String id;
    private String contenido;
    private Date fechaCreacion;
    private String autorId;
    private String autorNombre;
}
