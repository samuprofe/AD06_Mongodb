package org.example.mongodb.services;


import org.example.mongodb.dto.UsuarioDTO;
import org.example.mongodb.model.Usuario;
import org.example.mongodb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean agregarAmigo(String usuarioId, String amigoId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Usuario> amigoOpt = usuarioRepository.findById(amigoId);

        if (usuarioOpt.isPresent() && amigoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Usuario amigo = amigoOpt.get();

            usuario.agregarAmigo(amigo);
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    public boolean eliminarAmigo(String usuarioId, String amigoId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Usuario> amigoOpt = usuarioRepository.findById(amigoId);

        if (usuarioOpt.isPresent() && amigoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Usuario amigo = amigoOpt.get();

            usuario.eliminarAmigo(amigo);
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    public List<UsuarioDTO> getAmigos(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();


        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getAmigos().forEach(amigo -> {
                usuarioDTOList.add(this.convertToDTO(amigo));
            });
        }

        return usuarioDTOList;

        /*
        //Alternativa utilizando stream()
        if (usuarioOpt.isPresent()) {
            return usuario.getAmigos().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
        */

    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());

        List<String> amigosIds = usuario.getAmigos().stream()
                .map(Usuario::getId)
                .collect(Collectors.toList());

        dto.setAmigosIds(amigosIds);

        return dto;
    }
}