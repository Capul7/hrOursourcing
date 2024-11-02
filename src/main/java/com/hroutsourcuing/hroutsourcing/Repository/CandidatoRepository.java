package com.hroutsourcuing.hroutsourcing.Repository;

import com.hroutsourcuing.hroutsourcing.Models.modelCandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<modelCandidato, Long> {
        @Query("SELECT c FROM modelCandidato c WHERE c.id_postulacion.empresa.id_empresa = :idEmpresa " +
                "AND c.id_postulacion.empresa.usuario.id_usuario = :idUsuario")
        List<modelCandidato> findByEmpresaIdAndUsuarioId(@Param("idEmpresa") Long idEmpresa, @Param("idUsuario") Long idUsuario);
    }
