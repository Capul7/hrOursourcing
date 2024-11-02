package com.hroutsourcuing.hroutsourcing.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class registroDTO {
    private String email;
    private String password;
    private String nombre;
    private String direccion;
    private int telefono;
    private String descripcion;
    private String imagenBase64; // Para manejar la imagen como archivo en el front-end
}
