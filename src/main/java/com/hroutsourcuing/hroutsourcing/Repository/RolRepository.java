package com.hroutsourcuing.hroutsourcing.Repository;

import com.hroutsourcuing.hroutsourcing.Models.modelRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<modelRol, Long> {
    //metodo para buscar un rol por su nombre en la base de datos
    Optional<modelRol> findByNombre(String nombre);
}

