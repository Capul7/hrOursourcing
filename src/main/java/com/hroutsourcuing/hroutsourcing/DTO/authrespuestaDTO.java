package com.hroutsourcuing.hroutsourcing.DTO;

import lombok.Data;

@Data
public class authrespuestaDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String rolUsuario; // Agregar el campo para el rol
    private Long idUsuario; //
    private Long idEmpresa;


    // Constructor para token solamente
    public authrespuestaDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    // Constructor para token y rol
    public authrespuestaDTO(String accessToken, String rolUsuario, Long idUsuario, Long idEmpresa) {
        this.accessToken = accessToken;
        this.rolUsuario = rolUsuario;
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;

    }
}

