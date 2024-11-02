package com.hroutsourcuing.hroutsourcing.Models;
import com.hroutsourcuing.hroutsourcing.DTO.contactoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Data
@Table(name = "postulaciones")
public class modelPostulacion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idPostulaciones;
        private String titulo;
        private String descripcion;
        private Date fechaPostulacion;
        private Date fechaFinPostulacion;
        private Boolean estatus;
        private String imagen;
        @Getter
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_empresa")
        private modelEmpresa empresa; // Cambiado de id_empresa a empresa

        public Object isEstatus() {
                return this.estatus;
        }

}



