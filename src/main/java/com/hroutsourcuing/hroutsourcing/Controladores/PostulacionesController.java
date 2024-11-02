package com.hroutsourcuing.hroutsourcing.Controladores;

import com.hroutsourcuing.hroutsourcing.DTO.postulacionDTO;
import com.hroutsourcuing.hroutsourcing.DTO.postulaciondetalleDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelEmpresa;
import com.hroutsourcuing.hroutsourcing.Models.modelPostulacion;
import com.hroutsourcuing.hroutsourcing.Models.modelUsuario;
import com.hroutsourcuing.hroutsourcing.Repository.EmpresaRepository;
import com.hroutsourcuing.hroutsourcing.Repository.PostulacionRepository;
import com.hroutsourcuing.hroutsourcing.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("postulaciones")
public class PostulacionesController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Directorio donde se almacenarán las imágenes
    private final String uploadDir = "src/main/resources/static/";

    //crear postulacion
    @PostMapping("/crear")
    public ResponseEntity<String> crearPostulacion(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaFinPostulacion") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinPostulacion,
            @RequestParam("estatus") Boolean estatus,
            @RequestParam("imagen") MultipartFile imagen,
            Principal principal) { // Obtén el usuario autenticado

        try {
            // Obtén el email del usuario autenticado desde el principal
            String email = principal.getName();

            // Obtén el usuario por email y luego el id_empresa asociado
            modelUsuario usuario = usuariosRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            modelEmpresa empresa = empresaRepository.findByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            Long idEmpresa = empresa.getId(); // Obtén el ID de la empresa a partir de la entidad


            // Asegúrate de que el directorio de uploads existe
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path); // Crea el directorio si no existe
            }

            // Guardar la imagen en el servidor
            if (!imagen.isEmpty()) {
                String nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                Path filePath = path.resolve(nombreImagen);
                Files.write(filePath, imagen.getBytes());

                // Crear una nueva postulación
                modelPostulacion postulacion = new modelPostulacion();
                postulacion.setTitulo(titulo);
                postulacion.setDescripcion(descripcion);
                postulacion.setFechaPostulacion(new Date()); // Fecha actual
                postulacion.setFechaFinPostulacion(fechaFinPostulacion);
                postulacion.setEstatus(estatus);
                postulacion.setEmpresa(empresa);  // Relación con la empresa
                postulacion.setImagen(nombreImagen); // Guardamos el nombre de la imagen

                // Guardar la postulación en la base de datos
                postulacionRepository.save(postulacion);

                return new ResponseEntity<>("Postulación creada con éxito", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Debe proporcionar una imagen", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error al guardar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //obtener todas las postulaciones
    @GetMapping("/getAllPostulaciones")
    public List<postulacionDTO> getAllPostulaciones() {
        List<modelPostulacion> postulaciones = postulacionRepository.findAll();
        // Mapear las entidades a DTO
        return postulaciones.stream()
                .map(postulacion -> new postulacionDTO(
                        postulacion.getIdPostulaciones(),
                        postulacion.getTitulo(),
                        postulacion.getDescripcion(),
                        postulacion.getFechaPostulacion(),
                        postulacion.getFechaFinPostulacion(),
                        postulacion.isEstatus(),
                        postulacion.getImagen()
                ))
                .collect(Collectors.toList());
    }

    //obtener postulacion detalle//
    @GetMapping("/postulaciones/{id}")
    public ResponseEntity<postulaciondetalleDTO> getPostulacionById(@PathVariable Long id) {
        modelPostulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
        postulaciondetalleDTO dto = new postulaciondetalleDTO(
                postulacion.getIdPostulaciones(),

                postulacion.getTitulo(),
                postulacion.getDescripcion(),
                postulacion.getFechaPostulacion(),
                postulacion.getFechaFinPostulacion(),
                postulacion.getEstatus(),
                postulacion.getImagen()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/empresa/postulaciones/{idEmpresa}")
    public List<postulacionDTO> getPostulacionesByEmpresa(@PathVariable Long idEmpresa) {
        List<modelPostulacion> postulaciones = postulacionRepository.findByIdEmpresa(idEmpresa);
        return postulaciones.stream()
                .map(postulacion -> new postulacionDTO(
                        postulacion.getIdPostulaciones(),
                        postulacion.getTitulo(),
                        postulacion.getDescripcion(),
                        postulacion.getFechaPostulacion(),
                        postulacion.getFechaFinPostulacion(),
                        postulacion.isEstatus(),
                        postulacion.getImagen()
                ))
                .collect(Collectors.toList());
    }

}
