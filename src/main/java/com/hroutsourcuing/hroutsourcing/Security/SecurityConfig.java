package com.hroutsourcuing.hroutsourcing.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    // Bean para el AuthenticationManager
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Bean para el PasswordEncoder (BCrypt en este caso)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para el filtro de autenticación JWT
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // Bean para la cadena de filtros de seguridad
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("http://localhost:5500", "http://127.0.0.7:5500"));  // Permitir origen de frontend
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF si se usa JWT
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))  // Manejo personalizado de excepciones
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No mantener sesiones (JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()// Permitir acceso sin autenticación a /api/auth/**
                        .requestMatchers("contacto/enviarContacto").permitAll()// Permitir acceso sin autenticación
                        .requestMatchers("contacto/getAllContactos").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/imagenes/**").permitAll()
                        .requestMatchers("/postulaciones/postulaciones/{id}").permitAll()
                        .requestMatchers("/postulaciones/getAllPostulaciones").permitAll()
                        .requestMatchers("/auth/register").hasAnyAuthority("admin")
                        .requestMatchers("/empresa/detalles/{idUsuario}").hasAnyAuthority("empresa")
                        .requestMatchers("/admin/detalles/{idUsuario}").hasAnyAuthority("admin")
                        .requestMatchers("/auth/registerAdm").hasAnyAuthority("admin")
                        .requestMatchers("postulaciones/crear").hasAnyAuthority("empresa")//demomento esta permitir al despues de probarla se le otorgara a la empresa
                        //.requestMatchers("/candidatos/enviarCandidato").permitAll()
                        .requestMatchers("candidatos/postularCandidato").permitAll()
                        .requestMatchers("/candidato/**").permitAll()
                        .requestMatchers("/candidato/empresa/{idEmpresa}/usuario/{idUsuario}").hasAnyAuthority("empresa")
                        .requestMatchers("postulaciones/empresa/postulaciones/{idEmpresa}").hasAnyAuthority("empresa")
                        .anyRequest().authenticated())  // Cualquier otra petición debe estar autenticada
                .httpBasic(withDefaults());  // Configuración básica HTTP

        // Agregar el filtro JWT antes del UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
