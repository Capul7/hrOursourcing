package com.hroutsourcuing.hroutsourcing.Service;

import com.hroutsourcuing.hroutsourcing.DTO.empresaDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelEmpresa;
import com.hroutsourcuing.hroutsourcing.Repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<empresaDTO> findEmpresaDetailsByUsuarioId(Long idUsuario) {
        return empresaRepository.findEmpresaDetailsByUsuarioId(idUsuario);
    }


    public Long obtenerCorreoEmpresa(Long idEmpresa) {
        Optional<modelEmpresa> empresaOptional = empresaRepository.findById(idEmpresa);

        if (empresaOptional.isPresent()) {
            modelEmpresa empresa = empresaOptional.get();
            return empresa.getCorreo(); // Obtiene el email del usuario asociado
        } else {
            throw new EntityNotFoundException("Empresa no encontrada");
        }
    }

    /*// Método para obtener el correo de la empresa
    public String obtenerCorreoEmpresa(Long idEmpresa) {
        Optional<modelEmpresa> empresaOptional = empresaRepository.findById(idEmpresa);

        if (empresaOptional.isPresent()) {
            modelEmpresa empresa = empresaOptional.get();
            return empresa.getCorreo(); // Obtiene el email del usuario asociado
        } else {
            throw new EntityNotFoundException("Empresa no encontrada");
        }
    }*/
}
