package org.example.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMensajeDTO {
    private String contenido;
    private String autorId;  // Solo el ID del autor, no el objeto autor completo
}