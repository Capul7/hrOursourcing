package com.hroutsourcuing.hroutsourcing.Repository;

import com.hroutsourcuing.hroutsourcing.DTO.adminDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends JpaRepository<modelAdmin, Long>{
    @Query("SELECT new com.hroutsourcuing.hroutsourcing.DTO.adminDTO(e.nombre, e.direccion, e.telefono, e.descripcion, e.imagen, u.email) " +
            "FROM modelAdmin e " +
            "JOIN e.usuario u " +
            "WHERE u.id_usuario = :id_usuario") // Asegúrate de que id_usuario coincide con tu entidad modelUsuario
    List<adminDTO> findEmpresaDetailsByUsuarioId(@Param("id_usuario") Long id_usuario);
}
