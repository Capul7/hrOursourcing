package com.hroutsourcuing.hroutsourcing.Controladores;

import com.hroutsourcuing.hroutsourcing.DTO.contactoDTO;
import com.hroutsourcuing.hroutsourcing.Models.modelContacto;
import com.hroutsourcuing.hroutsourcing.Repository.ContactoRepository;
import com.hroutsourcuing.hroutsourcing.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contacto")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviarContacto")
    public ResponseEntity<modelContacto> enviarContacto(@RequestBody contactoDTO contactoDTO) {
        modelContacto contacto = new modelContacto();
        contacto.setTelefono(contactoDTO.getTelefono());
        contacto.setAsunto(contactoDTO.getAsunto());
        contacto.setNombre(contactoDTO.getNombre());
        contacto.setCorreo(contactoDTO.getCorreo());
        contacto.setDescripcion(contactoDTO.getDescripcion());

        // Guardar en la base de datos
        modelContacto nuevoContacto = contactoRepository.save(contacto);

        // Enviar correo al administrador
        String mensaje = "Nuevo contacto de: " + contactoDTO.getCorreo() +
                "\nAsunto: " + contactoDTO.getAsunto() +
                "\nnombre: " + contactoDTO.getNombre() +
                "\ntelefono: " + contactoDTO.getTelefono()+
                "\nDescripción: " + contactoDTO.getDescripcion();
        try {
            emailService.enviarCorreo("raulesgardo7@gmail.com", "Nuevo contacto recibido", mensaje);
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(nuevoContacto);
    }

    @GetMapping("/getAllContactos")
    public List<modelContacto> getAllContactos() {
        return contactoRepository.findAll();
    }
}

