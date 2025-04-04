package org.example.mongodb.controllers;


import org.example.mongodb.dto.UsuarioDTO;
import org.example.mongodb.model.Telefono;
import org.example.mongodb.model.Usuario;
import org.example.mongodb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();

        usuarioService.findAll().forEach(usuario -> {
            UsuarioDTO dto = usuarioService.convertToDTO(usuario);
            usuarios.add(dto);
        });

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable String id) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);

        if (usuarioOpt.isPresent()) {
            UsuarioDTO dto = usuarioService.convertToDTO(usuarioOpt.get());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        UsuarioDTO dto = usuarioService.convertToDTO(savedUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);

        if (usuarioOpt.isPresent()) {
            usuario.setId(id);  //El usuario que viene del cliente en el body normalmente no traerá el id porque ya está en la URL
            Usuario updatedUsuario = usuarioService.save(usuario);  //Se actualizan todos los datos, si no incluimos alguno se borraría, incluidos los teléfonos. Faltaría validación...
            UsuarioDTO dto = usuarioService.convertToDTO(updatedUsuario);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);

        if (usuarioOpt.isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{usuarioId}/telefonos")
    public ResponseEntity<Void> addTelefono(@PathVariable String usuarioId, @RequestBody Telefono numeroTelefono) {
        boolean resultado = usuarioService.addTelefono(usuarioId, numeroTelefono);

        if (resultado) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{usuarioId}/telefonos/{numeroTelefono}")
    public ResponseEntity<Void> removeTelefono(@PathVariable String usuarioId, @PathVariable String numeroTelefono) {
        boolean resultado = usuarioService.removeTelefono(usuarioId, numeroTelefono);

        if (resultado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}