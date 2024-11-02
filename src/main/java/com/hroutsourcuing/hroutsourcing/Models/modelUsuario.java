package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios") // Tabla usuarios
public class modelUsuario implements UserDetails {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Setter
    @Getter
    private String email; // Campo email

    @Setter
    @Getter
    private String password;

    // Relación con modelUser_roles
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<modelUser_roles> userRoles;

    // Relación con modelEmpresa (One-to-One si cada usuario tiene una sola empresa, ajusta si es diferente)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private modelEmpresa empresa;

    // Método para obtener los roles del usuario
    public List<modelRol> getRoles() {
        return userRoles.stream()
                .map(modelUser_roles::getRol)
                .collect(Collectors.toList());
    }

    // Método requerido para UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email; // Usamos el email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<modelUser_roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<modelUser_roles> userRoles) {
        this.userRoles = userRoles;
    }

    // Método setRoles actualizado
    public void setRoles(List<modelRol> roles) {
        if (userRoles == null) {
            userRoles = new ArrayList<>();
        }
        userRoles.clear();
        for (modelRol rol : roles) {
            modelUser_roles userRol = new modelUser_roles();
            userRol.setUsuario(this);
            userRol.setRol(rol);
            userRoles.add(userRol);
        }
    }

    public Long getId() {
        return this.id_usuario;
    }

    public modelEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(modelEmpresa empresa) {
        this.empresa = empresa;
    }
}
