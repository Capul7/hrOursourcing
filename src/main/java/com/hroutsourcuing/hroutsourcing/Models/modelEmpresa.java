package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "empresa")
public class modelEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_empresa;

    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion;
    private String imagen;

    // Relación con modelUsuario (One-to-One si cada empresa está asociada a un solo usuario)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private modelUsuario usuario;

    public modelEmpresa() {
        this.imagen = imagen;
    }

    public Long getId() {
        return this.id_empresa;
    }

    public Long getCorreo() {
        return this.getId_empresa();
    }
}
