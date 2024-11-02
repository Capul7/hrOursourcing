package com.hroutsourcuing.hroutsourcing.Controladores;


import com.hroutsourcuing.hroutsourcing.DTO.adminDTO;
import com.hroutsourcuing.hroutsourcing.DTO.authrespuestaDTO;
import com.hroutsourcuing.hroutsourcing.DTO.loginDTO;
import com.hroutsourcuing.hroutsourcing.DTO.registroDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelRol;
import com.hroutsourcuing.hroutsourcing.Models.modelUsuario;
import com.hroutsourcuing.hroutsourcing.Repository.RolRepository;
import com.hroutsourcuing.hroutsourcing.Repository.UsuariosRepository;
import com.hroutsourcuing.hroutsourcing.Security.JwtGenerador;
import com.hroutsourcuing.hroutsourcing.Service.EmailService;
import com.hroutsourcuing.hroutsourcing.Service.RolService;
import jakarta.transaction.TransactionScoped;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;


@RestController
@RequestMapping("/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RolRepository rolRepository;
    private UsuariosRepository usuariosRepository;
    private JwtGenerador jwtGenerador;
    private adminDTO usuarios;

    @Autowired

    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolRepository rolRepository, UsuariosRepository usuariosRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.usuariosRepository = usuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }

    @Autowired
    private EmailService emailService;

    @Autowired
    private RolService rolService;

    private final String uploadDir = "src/main/resources/static/"; // Ruta donde se guardan las imágenes


    @PostMapping("register")
    public ResponseEntity<String> registrarUsuarioEmpresa(
            @RequestParam("email") String email,
            @RequestParam("nombre") String nombreEmpresa,
            @RequestParam("direccion") String direccionEmpresa,
            @RequestParam("telefono") String telefonoEmpresa,
            @RequestParam("descripcion") String descripcionEmpresa,
            @RequestParam("imagen") MultipartFile imagen) {

        try {
            // Verificar si el usuario ya existe
            if (usuariosRepository.existsByEmail(email)) {
                return new ResponseEntity<>("El usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
            }

            // Asegúrate de que el directorio de uploads existe
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Generar una contraseña aleatoria
            String passwordAleatoria = jwtGenerador.generarContrasenaAleatoria(10);
            String passwordEncriptada = passwordEncoder.encode(passwordAleatoria);

            // Guardar la imagen en el servidor
            String nombreImagen = null;
            if (!imagen.isEmpty()) {
                nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path filePath = path.resolve(nombreImagen);
                Files.write(filePath, imagen.getBytes());
            } else {
                return new ResponseEntity<>("Debe proporcionar una imagen", HttpStatus.BAD_REQUEST);
            }

            // Llamar al procedimiento almacenado para registrar el usuario y la empresa
            try {
                usuariosRepository.registrarUsuarioEmpresa(
                        email,
                        passwordEncriptada,
                        nombreEmpresa,
                        direccionEmpresa,
                        telefonoEmpresa,
                        descripcionEmpresa,
                        nombreImagen
                );
            } catch (Exception e) {
                return new ResponseEntity<>("Error al ejecutar el procedimiento almacenado: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Enviar un correo con la contraseña generada
            emailService.enviarCorreoBienvenida(email, passwordAleatoria);

            return new ResponseEntity<>("Registro de empresa exitoso", HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al guardar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(
            @RequestParam("email") String email,
            @RequestParam("nombre") String nombreAdmin,
            @RequestParam("direccion") String direccionAdmin,
            @RequestParam("telefono") String telefonoAdmin,
            @RequestParam("descripcion") String descripcionAdmin,
            @RequestParam("imagen") MultipartFile imagen) {

        try {
            // Verificar si el usuario ya existe
            if (usuariosRepository.existsByEmail(email)) {
                return new ResponseEntity<>("El usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
            }

            // Generar una contraseña aleatoria y encriptarla
            String passwordAleatoria = jwtGenerador.generarContrasenaAleatoria(10);
            String passwordEncriptada = passwordEncoder.encode(passwordAleatoria);

            // Guardar la imagen en el servidor
            String nombreImagen = null;
            if (!imagen.isEmpty()) {
                Path path = Paths.get("src/main/resources/static");
                Files.createDirectories(path);
                nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Files.write(path.resolve(nombreImagen), imagen.getBytes());
            }

            // Llamar al procedimiento almacenado para registrar el admin
            usuariosRepository.registrarUsuarioAdmin(
                    email,
                    passwordEncriptada,
                    nombreAdmin,
                    direccionAdmin,
                    telefonoAdmin,
                    descripcionAdmin,
                    nombreImagen
            );

            // Enviar un correo con la contraseña generada
            emailService.enviarCorreoBienvenida(email, passwordAleatoria);

            return new ResponseEntity<>("Registro de admin exitoso", HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al guardar la imagen: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<authrespuestaDTO> login(@RequestBody loginDTO dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoLogin.getEmail(), dtoLogin.getPassword())
        );

        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String token = jwtGenerador.generarToken(authentication);

        // Obtener el usuario logueado y su rol
        modelUsuario usuario = (modelUsuario) authentication.getPrincipal();
        String rolUsuario = usuario.getUserRoles().stream()
                .map(userRole -> userRole.getRol().getNombre())
                .findFirst()
                .orElse("empresa");

        // Obtener el id de empresa asociado al usuario, si existe
        Long idEmpresa = null;
        if (usuario.getEmpresa() != null) {
            idEmpresa = usuario.getEmpresa().getId_empresa();  // Ajusta según tu modelo
        }

        // Incluir idUsuario, rol e idEmpresa en la respuesta
        authrespuestaDTO respuesta = new authrespuestaDTO(token, rolUsuario, usuario.getId(), idEmpresa);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    //Método para poder logear un usuario y obtener un token
   /* @PostMapping("login")
    public ResponseEntity<authrespuestaDTO> login(@RequestBody loginDTO dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoLogin.getEmail(), dtoLogin.getPassword())
        );
        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generar el token JWT
        String token = jwtGenerador.generarToken(authentication);

        // Aquí ahora puedes hacer el cast a modelUsuario directamente
        modelUsuario usuario = (modelUsuario) authentication.getPrincipal();
        // Obtener el rol del usuario
        String rolUsuario = usuario.getUserRoles().stream()
                .map(userRole -> userRole.getRol().getNombre())
                .findFirst()
                .orElse("empresa");


        // Incluimos también el id del usuario en la respuesta
        return new ResponseEntity<>(new authrespuestaDTO(token, rolUsuario, usuario.getId(), idEmpresa), HttpStatus.OK);
    }*/


    @PostMapping("logout")
    public ResponseEntity<String> logout() {
        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();

        return new ResponseEntity<>("Logout exitoso", HttpStatus.OK);
    }
}