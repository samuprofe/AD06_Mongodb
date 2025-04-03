package org.example.mongodb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String nombre;
    private String email;
    private String password;

    @DBRef
    private List<Usuario> amigos = new ArrayList<>();

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public void agregarAmigo(Usuario amigo) {
        if (!this.amigos.contains(amigo)) {
            this.amigos.add(amigo);
        }
    }

    public void eliminarAmigo(Usuario amigo) {
        this.amigos.remove(amigo);
    }
}