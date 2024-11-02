package com.hroutsourcuing.hroutsourcing.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
@Data
@Getter
@Setter
public class postulaciondetalleDTO {
    private Long idPostulaciones;
    private String titulo;
    private String descripcion;
    private Date fechaPostulacion;
    private Date fechaFinPostulacion;
    private Boolean estatus;
    private String imagen;

    public postulaciondetalleDTO(Long idPostulaciones, String titulo, String descripcion, Date fechaPostulacion, Date fechaFinPostulacion, Boolean estatus, String imagen) {
        this.idPostulaciones = idPostulaciones;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPostulacion = fechaPostulacion;
        this.fechaFinPostulacion = fechaFinPostulacion;
        this.estatus = (boolean) estatus;
        this.imagen = imagen;
    }
}
