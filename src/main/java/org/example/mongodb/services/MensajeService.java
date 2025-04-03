package org.example.mongodb.services;


import org.example.mongodb.dto.MensajeDTO;
import org.example.mongodb.model.Mensaje;
import org.example.mongodb.model.Usuario;
import org.example.mongodb.repositories.MensajeRepository;
import org.example.mongodb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<MensajeDTO> findAll() {
        return mensajeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MensajeDTO> findById(String id) {
        return mensajeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MensajeDTO save(String contenido, String autorId) {
        Optional<Usuario> autorOpt = usuarioRepository.findById(autorId);

        if (autorOpt.isPresent()) {
            Usuario autor = autorOpt.get();
            Mensaje mensaje = new Mensaje(contenido, autor);
            Mensaje savedMensaje = mensajeRepository.save(mensaje);
            return convertToDTO(savedMensaje);
        }

        return null;
    }

    public void deleteById(String id) {
        mensajeRepository.deleteById(id);
    }

    public List<MensajeDTO> findByAutor(String autorId) {
        Optional<Usuario> autorOpt = usuarioRepository.findById(autorId);

        if (autorOpt.isPresent()) {
            Usuario autor = autorOpt.get();
            return mensajeRepository.findByAutorOrderByFechaCreacionDesc(autor).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    public MensajeDTO convertToDTO(Mensaje mensaje) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setContenido(mensaje.getContenido());
        dto.setFechaCreacion(mensaje.getFechaCreacion());

        Usuario autor = mensaje.getAutor();
        if (autor != null) {
            dto.setAutorId(autor.getId());
            dto.setAutorNombre(autor.getNombre());
        }

        return dto;
    }
}
