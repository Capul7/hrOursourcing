package com.hroutsourcuing.hroutsourcing.Service;

import com.hroutsourcuing.hroutsourcing.DTO.adminDTO;
import com.hroutsourcuing.hroutsourcing.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<adminDTO> findAdminDetailsByUsuarioId(Long id_usuario) {
        return adminRepository.findEmpresaDetailsByUsuarioId(id_usuario);
    }
}
