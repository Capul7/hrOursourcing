package com.hroutsourcuing.hroutsourcing.Controladores;

import com.hroutsourcuing.hroutsourcing.DTO.empresaDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelEmpresa;
import com.hroutsourcuing.hroutsourcing.Repository.EmpresaRepository;
import com.hroutsourcuing.hroutsourcing.Service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/detalles/{idUsuario}")
    public ResponseEntity<List<empresaDTO>> obtenerDetallesEmpresaPorUsuarioId(@PathVariable Long idUsuario) {
        List<empresaDTO> empresaDetalles = empresaService.findEmpresaDetailsByUsuarioId(idUsuario);
        if (empresaDetalles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentran detalles, retornamos 404
        }
        return new ResponseEntity<>(empresaDetalles, HttpStatus.OK); // Si se encuentran detalles, retornamos 200
    }
}

    /*@PostMapping("/registrar")
    public ResponseEntity<modelEmpresa> registrarEmpresa(@RequestBody empresaDTO empresaDTO) {
        modelEmpresa empresa = new modelEmpresa();
        // Mapear los datos del DTO a la entidad
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setDireccion(empresaDTO.getDireccion());
        empresa.setTelefono(empresaDTO.getTelefono());
        // Aquí lógica para enviar correo
        modelEmpresa nuevaEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.ok(nuevaEmpresa);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<modelEmpresa> modificarEmpresa(@PathVariable Long id, @RequestBody empresaDTO empresaDTO) {
        Optional<modelEmpresa> empresaOpt = empresaRepository.findById(id);
        if (empresaOpt.isPresent()) {
            modelEmpresa empresa = empresaOpt.get();
            empresa.setNombre(empresaDTO.getNombre());
            empresa.setDireccion(empresaDTO.getDireccion());
            empresa.setTelefono(empresaDTO.getTelefono());
            return ResponseEntity.ok(empresaRepository.save(empresa));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        Optional<modelEmpresa> empresaOpt = empresaRepository.findById(id);
        if (empresaOpt.isPresent()) {
            empresaRepository.delete(empresaOpt.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
*/