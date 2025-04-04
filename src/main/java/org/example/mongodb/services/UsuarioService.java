package org.example.mongodb.services;


import org.example.mongodb.dto.UsuarioDTO;
import org.example.mongodb.model.Telefono;
import org.example.mongodb.model.Usuario;
import org.example.mongodb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    public boolean addTelefono(String usuarioId, Telefono telefono) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Inicializar la lista si es null
            if (usuario.getTelefonos() == null) {
                usuario.setTelefonos(new ArrayList<>());
            }

            usuario.getTelefonos().add(telefono);
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    public boolean removeTelefono(String usuarioId, String numeroTelefono) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getTelefonos() != null) {
                boolean removed = usuario.getTelefonos().removeIf(t -> t.getNumero().equals(numeroTelefono));

                if (removed) {
                    usuarioRepository.save(usuario);
                    return true;
                }
            }
        }

        return false;
    }

    public List<Telefono> getTelefonos(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getTelefonos() != null ? usuario.getTelefonos() : List.of();
        }

        return List.of();
    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        if(usuario.getTelefonos() != null) {
            dto.setTelefonos(usuario.getTelefonos());
        }else{
            //Si no existen teléfonos en la colección devolvemos un array vacío en lugar de null. Así en el Frontend simplemente se recorrerá el Array.
            dto.setTelefonos(new ArrayList<>());
        }


        return dto;
    }
}

/*
@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());

        return dto;
    }
}

 */