package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "candidato")
public class modelCandidato {

    // Getters y Setters
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idCandidato;

        private String nombre;
        private String correo;
        private int telefono;

        @Lob
        private byte[] cv;

        @Lob
        private byte[] foto;  // Campo para almacenar la foto

        @ManyToOne
        @JoinColumn(name = "id_postulaciones")
        private modelPostulacion id_postulacion;

        @ManyToOne
        @JoinColumn(name = "id_rol")
        private modelRol id_rol;

    public void setIdPostulaciones(int idPostulaciones) {
        }

        public void setIdRol(int idRol) {
        }
}
