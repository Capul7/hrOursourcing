package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contacto")
public class modelContacto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idContacto;
        private int telefono;
        private String asunto;
        private String nombre;
        private String correo;
        private String descripcion;

}


