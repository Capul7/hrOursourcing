package com.hroutsourcuing.hroutsourcing.Service;

import com.hroutsourcuing.hroutsourcing.Repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hroutsourcuing.hroutsourcing.Models.modelPostulacion;
import java.util.Optional;

@Service
public class PostulacionService{

    @Autowired
    private  PostulacionRepository postulacionRepository;

    public modelPostulacion obtenerPostulacionPorId(Long id) {
        Optional<modelPostulacion> postulacion = postulacionRepository.findById(id);
        if (postulacion.isPresent()) {
            return postulacion.get();
        } else {
            throw new IllegalArgumentException("No se encontró la postulación con el ID: " + id);
        }
    }
}

