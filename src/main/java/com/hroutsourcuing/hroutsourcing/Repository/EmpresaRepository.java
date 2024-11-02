package com.hroutsourcuing.hroutsourcing.Repository;


import com.hroutsourcuing.hroutsourcing.DTO.empresaDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<modelEmpresa, Long> {

    @Query("SELECT new com.hroutsourcuing.hroutsourcing.DTO.empresaDTO(e.nombre, e.direccion, e.telefono, e.descripcion, e.imagen, u.email) " +
            "FROM modelEmpresa e " +
            "JOIN e.usuario u " +
            "WHERE u.id_usuario = :id_usuario") // Asegúrate de que id_usuario coincide con tu entidad modelUsuario
    List<empresaDTO> findEmpresaDetailsByUsuarioId(@Param("id_usuario") Long id_usuario);

    Optional<modelEmpresa> findByUsuarioId(Long id);
}




