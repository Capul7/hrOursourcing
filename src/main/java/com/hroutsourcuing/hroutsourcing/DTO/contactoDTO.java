package com.hroutsourcuing.hroutsourcing.DTO;

public class contactoDTO {
    private int telefono;
    private String asunto;
    private String nombre;
    private String correo;
    private String descripcion;



    @Override
    public String toString() {
        return "contactoDTO{" +
                "telefono='" + telefono + '\'' +
                ", asunto='" + asunto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public contactoDTO(int telefono, String asunto, String nombre, String correo, String descripcion) {
        this.telefono = telefono;
        this.asunto = asunto;
        this.nombre = nombre;
        this.correo = correo;
        this.descripcion = descripcion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
