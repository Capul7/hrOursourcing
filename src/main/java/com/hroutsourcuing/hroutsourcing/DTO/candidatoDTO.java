package com.hroutsourcuing.hroutsourcing.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
public class candidatoDTO {
    private long idCandidato;
    private String nombre;
    private String correo;
    private int telefono;
    private Long idPostulacion; // ID de la postulación
    private String cv; // Archivo CV
    private String foto; // Asegúrate de que sea una cadena en base64

    public candidatoDTO(Long id, String nombre, String correo, int telefono, byte[] cv, byte[] foto) {
        this.idPostulacion = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.cv = (cv != null) ? Base64.getEncoder().encodeToString(cv) : ""; // Manejo seguro de null
        this.foto = (foto != null) ? Base64.getEncoder().encodeToString(foto) : ""; // Manejo seguro de null
    }


}

