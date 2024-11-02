package com.hroutsourcuing.hroutsourcing.Repository;

import com.hroutsourcuing.hroutsourcing.Models.modelPostulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulacionRepository extends JpaRepository<modelPostulacion, Long> {
    Optional<modelPostulacion> findById(Long id);

    @Query("SELECT p FROM modelPostulacion p WHERE p.empresa.id = :idEmpresa")
    List<modelPostulacion> findByIdEmpresa(@Param("idEmpresa") Long idEmpresa);
}
