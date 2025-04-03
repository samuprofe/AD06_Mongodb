package org.example.mongodb.controllers;


import org.example.mongodb.dto.UsuarioDTO;
import org.example.mongodb.model.Usuario;
import org.example.mongodb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<UsuarioDTO> usuarios = usuarioService.findAll().stream()
                .map(usuarioService::convertToDTO)
                .collect(Collectors.toList());

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
            Usuario updatedUsuario = usuarioService.save(usuario);
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

    @PostMapping("/{usuarioId}/amigos/{amigoId}")
    public ResponseEntity<Void> agregarAmigo(@PathVariable String usuarioId, @PathVariable String amigoId) {
        boolean resultado = usuarioService.agregarAmigo(usuarioId, amigoId);

        if (resultado) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{usuarioId}/amigos/{amigoId}")
    public ResponseEntity<Void> eliminarAmigo(@PathVariable String usuarioId, @PathVariable String amigoId) {
        boolean resultado = usuarioService.eliminarAmigo(usuarioId, amigoId);

        if (resultado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{usuarioId}/amigos")
    public ResponseEntity<List<UsuarioDTO>> getAmigos(@PathVariable String usuarioId) {
        List<UsuarioDTO> amigos = usuarioService.getAmigos(usuarioId);
        return ResponseEntity.ok(amigos);
    }
}