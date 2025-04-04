package org.example.mongodb.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection = "mensajes")
public class Mensaje {

    @Id
    private String id;
    private String contenido;
    private LocalDate fechaCreacion;

    @DBRef
    private Usuario autor;

    public Mensaje(String contenido, Usuario autor) {
        this.contenido = contenido;
        this.autor = autor;
        this.fechaCreacion = LocalDate.now();
    }
}

