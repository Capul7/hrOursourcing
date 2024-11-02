package com.hroutsourcuing.hroutsourcing.Service;
import com.hroutsourcuing.hroutsourcing.DTO.candidatoDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelCandidato;
import com.hroutsourcuing.hroutsourcing.Repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public CandidatoService(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    public List<candidatoDTO> getCandidatosByEmpresaAndUsuario(Long idEmpresa, Long idUsuario) {
        return candidatoRepository.findByEmpresaIdAndUsuarioId(idEmpresa, idUsuario).stream()
                .map(candidato -> new candidatoDTO(
                        candidato.getIdCandidato(),
                        candidato.getNombre(),
                        candidato.getCorreo(),
                        candidato.getTelefono(),
                        candidato.getCv(),
                        candidato.getFoto() // Asegúrate de obtener el campo foto correctamente
                ))
                .collect(Collectors.toList());
    }


    // Método para convertir de `modelCandidato` a `candidatoDTO`
    private candidatoDTO convertToDTO(modelCandidato candidato) {
        return new candidatoDTO(
                candidato.getId_postulacion().getIdPostulaciones(),
                candidato.getNombre(),
                candidato.getCorreo(),
                candidato.getTelefono(),
                candidato.getCv(),
                candidato.getFoto()
        );
    }

}



