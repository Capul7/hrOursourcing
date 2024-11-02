package com.hroutsourcuing.hroutsourcing.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class adminDTO {
    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion;
    private String imagen;
    private String email;

    // Constructor
    public adminDTO(String nombre, String direccion, String telefono, String descripcion, String imagen, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.email = email;
    }

    // Getters y setters
}
