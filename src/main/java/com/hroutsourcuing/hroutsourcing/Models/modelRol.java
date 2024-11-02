package com.hroutsourcuing.hroutsourcing.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class modelRol {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idRol;

        @Getter
        @Setter
        private String nombre;

        @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @Getter
        @Setter
        private List<modelUser_roles> userRoles;

        public int getId() {
                return 0;
        }
}
