package com.hroutsourcuing.hroutsourcing.Service;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmpresaService empresaService;  // Inyecta el servicio de empresa
    private byte[] fileBytes;

    public void enviarCorreo(String destinatario, String asunto, String mensaje) throws MailException {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(destinatario);
            mailMessage.setSubject(asunto);
            mailMessage.setText(mensaje);
            mailMessage.setFrom("noreply@segatest.xyz");
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            throw e;  // Relanzar la excepción para que pueda ser manejada a otro nivel
        }
    }

    public void enviarCorreoBienvenida(String email, String password) {
        String asunto = "Bienvenido a Reclutamiento Outsourcing";
        String mensaje = "Te damos la bienvenida a Reclutamiento Outsourcing.\n\n"
                + "Tus datos de inicio de sesión son:\n"
                + "Correo: " + email + "\n"
                + "Contraseña: " + password + "\n\n"
                + "Gracias por unirte a nosotros.\n"
                + "Reclutamiento HR Outsourcing";

        try {
            // Crear el mensaje de correo
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(asunto);
            mailMessage.setText(mensaje);
            mailMessage.setFrom("noreply@segatest.xyz"); // Asegúrate de que este correo esté configurado correctamente

            // Enviar el correo
            mailSender.send(mailMessage);
            System.out.println("Correo enviado exitosamente a " + email);

        } catch (MailException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            // Opcional: relanzar la excepción si deseas manejarla a nivel superior
            throw e;
        }
    }


    // Enviar correo con archivo adjunto
    public void enviarCorreoConAdjunto(String from, String to, String subject, String body, String fileName, byte[] cvBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true para permitir archivos adjuntos

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        // Crear un DataSource para el archivo adjunto
        DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
        helper.addAttachment(fileName, dataSource);

        mailSender.send(message);
    }

}
