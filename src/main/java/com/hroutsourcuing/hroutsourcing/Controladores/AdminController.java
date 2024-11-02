package com.hroutsourcuing.hroutsourcing.Controladores;

import com.hroutsourcuing.hroutsourcing.DTO.adminDTO;
import com.hroutsourcuing.hroutsourcing.DTO.empresaDTO;
import com.hroutsourcuing.hroutsourcing.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/detalles/{idUsuario}")
    public ResponseEntity<List<adminDTO>> obtenerDetallesAdminPorUsuarioId(@PathVariable Long idUsuario) {
        List<adminDTO> adminDetalles = adminService.findAdminDetailsByUsuarioId(idUsuario);
        if (adminDetalles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentran detalles, retornamos 404
        }
        return new ResponseEntity<>(adminDetalles, HttpStatus.OK); // Si se encuentran detalles, retornamos 200
    }

}
