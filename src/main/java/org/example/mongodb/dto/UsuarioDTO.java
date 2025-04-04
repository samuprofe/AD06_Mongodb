package org.example.mongodb.dto;


import lombok.*;
import org.example.mongodb.model.Telefono;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String email;
    private List<Telefono> telefonos;
}