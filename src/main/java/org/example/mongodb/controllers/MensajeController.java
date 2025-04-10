package org.example.mongodb.controllers;


import org.example.mongodb.dto.CreateMensajeDTO;
import org.example.mongodb.dto.MensajeDTO;
import org.example.mongodb.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @GetMapping
    public ResponseEntity<List<MensajeDTO>> getAllMensajes() {
        List<MensajeDTO> mensajes = mensajeService.findAll();
        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO> getMensajeById(@PathVariable String id) {
        Optional<MensajeDTO> mensajeOpt = mensajeService.findById(id);

        return mensajeOpt
                .map(mensaje -> ResponseEntity.ok(mensaje))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MensajeDTO> createMensaje(@RequestBody CreateMensajeDTO createMensaje) {

        //Podríamos sustituir esta validación por validación con etiquetas en la clase CreateMensajeDTO
        if (createMensaje.getContenido() == null || createMensaje.getEmailAutor() == null) {
            return ResponseEntity.badRequest().build();
        }

        MensajeDTO savedMensaje = mensajeService.save(createMensaje);

        if (savedMensaje == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMensaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable String id) {
        Optional<MensajeDTO> mensajeOpt = mensajeService.findById(id);

        if (mensajeOpt.isPresent()) {
            mensajeService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<MensajeDTO>> getMensajesByAutor(@PathVariable String autorId) {
        List<MensajeDTO> mensajes = mensajeService.findByAutor(autorId);
        return ResponseEntity.ok(mensajes);
    }
}