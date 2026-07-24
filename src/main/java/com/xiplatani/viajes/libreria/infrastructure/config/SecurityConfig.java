package com.xiplatani.viajes.libreria.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xiplatani.viajes.libreria.infrastructure.security.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/v1/api/auth/register", "/v1/api/auth/login", "/v1/api/seed").permitAll()
                        .requestMatchers("/v1/api/auth/refresh-token").authenticated()

                        // Rutas exclusivas del Administrador
                        .requestMatchers("/v1/api/admin/**").hasRole("ADMIN")

                        // Rutas públicas / lector para consulta de libros
                        .requestMatchers(HttpMethod.GET, "/v1/api/books", "/v1/api/books/{id}").permitAll()

                        // Rutas de solicitudes para lectores autenticados
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/{id}/request-loan").authenticated()
                        .requestMatchers(HttpMethod.GET, "/v1/api/books/requests/my-requests").authenticated()

                        // Rutas del Administrador para gestión de catálogo de libros
                        .requestMatchers(HttpMethod.POST, "/v1/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v1/api/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/api/books/{id}").hasRole("ADMIN")

                        // Rutas de aprobación/devolución para Bibliotecario y Administrador
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/approve")
                        .hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/reject")
                        .hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/return")
                        .hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/v1/api/books/requests/pending")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
