package org.example.mongodb.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Telefono {
    private String numero;
    private String tipo;
}
