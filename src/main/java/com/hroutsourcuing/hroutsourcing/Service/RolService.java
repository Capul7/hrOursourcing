package com.hroutsourcuing.hroutsourcing.Service;

import com.hroutsourcuing.hroutsourcing.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hroutsourcuing.hroutsourcing.Models.modelRol;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private static RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        RolService.rolRepository = rolRepository;
    }

    public modelRol obtenerRolPorId(Long id) {
        Optional<modelRol> rol = rolRepository.findById(id);
        if (rol.isPresent()) {
            return rol.get();
        } else {
            throw new IllegalArgumentException("No se encontró el rol con el ID: " + id);
        }
    }

    // Método para obtener el ID del rol a partir de su nombre
    public static int obtenerIdRol(String nombreRol) {
        Optional<modelRol> rolOpt = rolRepository.findByNombre(nombreRol);
        if (rolOpt.isPresent()) {
            return rolOpt.get().getId(); // Asumimos que modelRol tiene un campo id
        } else {
            throw new RuntimeException("Rol no encontrado: " + nombreRol);
        }
    }

}

