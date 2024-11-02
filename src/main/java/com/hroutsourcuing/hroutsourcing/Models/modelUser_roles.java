package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
public class modelUser_roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private modelUsuario usuario;

    // Ajuste en el getter para devolver el tipo correcto
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    private modelRol rol;

}

