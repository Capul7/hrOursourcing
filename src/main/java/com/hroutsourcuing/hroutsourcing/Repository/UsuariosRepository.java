package com.hroutsourcuing.hroutsourcing.Repository;

import com.hroutsourcuing.hroutsourcing.Models.modelUsuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<modelUsuario, Long> {
//metodo para buscar un usuario mediante su correo
    Optional<modelUsuario> findByEmail(String email);
    //metodo para poder verificar si el usuario existe en la base de datos
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "CALL crear_usuario_empresa(:email, :password, :nombreEmpresa, :direccionEmpresa, :telefonoEmpresa, :descripcionEmpresa, :imagenEmpresa)", nativeQuery = true)
    int registrarUsuarioEmpresa(
            @Param("email") String email,
            @Param("password") String password,
            @Param("nombreEmpresa") String nombreEmpresa,
            @Param("direccionEmpresa") String direccionEmpresa,
            @Param("telefonoEmpresa") String telefonoEmpresa,
            @Param("descripcionEmpresa") String descripcionEmpresa,
            @Param("imagenEmpresa") String imagenEmpresa
    );

    // Procedimiento para registrar un administrador
    @Modifying
    @Transactional
    @Query(value = "CALL crear_usuario_admin(:email, :password, :nombreAdmin, :direccionAdmin, :telefonoAdmin, :descripcionAdmin, :imagenAdmin)", nativeQuery = true)
    int registrarUsuarioAdmin(
            @Param("email") String email,
            @Param("password") String password,
            @Param("nombreAdmin") String nombreAdmin,
            @Param("direccionAdmin") String direccionAdmin,
            @Param("telefonoAdmin") String telefonoAdmin,
            @Param("descripcionAdmin") String descripcionAdmin,
            @Param("imagenAdmin") String imagenAdmin
    );

}


