package org.example.mongodb.repositories;


import org.example.mongodb.model.Mensaje;
import org.example.mongodb.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    List<Mensaje> findByAutorOrderByFechaCreacionDesc(Usuario autor);
}
