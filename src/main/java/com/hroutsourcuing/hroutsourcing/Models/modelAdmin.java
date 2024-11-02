package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class modelAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_admin;

    private String nombre;
    private String direccion;
    private String telefono; // Cambiado a String
    private String descripcion;
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private modelUsuario usuario; // Cambiado de id_usuario a usuario

    public modelAdmin(){
        this.imagen = imagen;
    }
}
