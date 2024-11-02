package com.hroutsourcuing.hroutsourcing.DTO;

import com.hroutsourcuing.hroutsourcing.Models.modelEmpresa;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class postulacionDTO{
        private Long idPostulaciones;
        private String titulo;
        private String descripcion;
        private Date fechaPostulacion;
        private Date fechaFinPostulacion;
        private boolean estatus;
        private String imagen;


        public postulacionDTO(Long idPostulaciones, String titulo, String descripcion, Date fechaPostulacion, Date fechaFinPostulacion, Object estatus, String imagen) {
                this.idPostulaciones = idPostulaciones;
                this.titulo = titulo;
                this.descripcion = descripcion;
                this.fechaPostulacion = fechaPostulacion;
                this.fechaFinPostulacion = fechaFinPostulacion;
                this.estatus = (boolean) estatus;
                this.imagen = imagen;
        }
}
