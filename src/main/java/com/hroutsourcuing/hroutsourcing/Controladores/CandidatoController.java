package com.hroutsourcuing.hroutsourcing.Controladores;

import com.hroutsourcuing.hroutsourcing.DTO.candidatoDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelCandidato;
import com.hroutsourcuing.hroutsourcing.Models.modelPostulacion;
import com.hroutsourcuing.hroutsourcing.Models.modelRol;
import com.hroutsourcuing.hroutsourcing.Repository.CandidatoRepository;
import com.hroutsourcuing.hroutsourcing.Repository.EmpresaRepository;
import com.hroutsourcuing.hroutsourcing.Repository.PostulacionRepository;
import com.hroutsourcuing.hroutsourcing.Repository.RolRepository;
import com.hroutsourcuing.hroutsourcing.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PostulacionService postulacionService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RolService rolService;

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/empresa/{idEmpresa}/usuario/{idUsuario}")
    public ResponseEntity<List<candidatoDTO>> getCandidatosByEmpresaAndUsuario(
            @PathVariable Long idEmpresa,
            @PathVariable Long idUsuario) {
        List<candidatoDTO> candidatos = candidatoService.getCandidatosByEmpresaAndUsuario(idEmpresa, idUsuario);
        return ResponseEntity.ok(candidatos);
    }

    @PostMapping("/postularCandidato")
    public ResponseEntity<String> enviarCandidato(
            @RequestParam("id_postulacion") Long idPostulacion,
            @RequestParam("nombre") String nombre,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("foto") MultipartFile foto) {

        try {
            // Validar el ID de la postulación
            if (idPostulacion == null || idPostulacion <= 0) {
                return ResponseEntity.badRequest().body("El ID de la postulación es inválido.");
            }

            // Obtener la postulación a partir del ID
            modelPostulacion postulacion = postulacionService.obtenerPostulacionPorId(idPostulacion);
            if (postulacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la postulación con el ID: " + idPostulacion);
            }

            // Convertir los archivos `cv` y `foto` a byte[]
            byte[] cvBytes = cv.getBytes();
            byte[] fotoBytes = foto.getBytes();

            if (cvBytes == null || cvBytes.length == 0) {
                return ResponseEntity.badRequest().body("El archivo CV está vacío o no se ha subido correctamente.");
            }
            if (fotoBytes == null || fotoBytes.length == 0) {
                return ResponseEntity.badRequest().body("El archivo foto está vacío o no se ha subido correctamente.");
            }

            // Crear un nuevo candidato
            modelCandidato candidato = new modelCandidato();
            candidato.setNombre(nombre);
            candidato.setCorreo(correo);
            candidato.setTelefono(Integer.parseInt(telefono));  // Asegurarse de que sea un número
            candidato.setCv(cvBytes);
            candidato.setFoto(fotoBytes);  // Asignar la foto al modelo

            // Obtener el rol (por ejemplo, rol de candidato)
            modelRol rol = rolService.obtenerRolPorId(3L);
            candidato.setId_postulacion(postulacion);
            candidato.setId_rol(rol);

            // Guardar el candidato en la base de datos
            candidatoRepository.save(candidato);

            // Respuesta exitosa
            return ResponseEntity.ok("Candidato enviado y almacenado exitosamente en la base de datos.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al enviar el candidato: " + e.getMessage());
        }
    }


}
