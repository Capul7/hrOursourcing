package com.hroutsourcuing.hroutsourcing.Repository;



import com.hroutsourcuing.hroutsourcing.Models.modelContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<modelContacto, Long> {
}
