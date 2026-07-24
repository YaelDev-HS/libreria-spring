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
                        .requestMatchers("/v1/auth/register", "/v1/auth/login", "/v1/api/seed").permitAll()
                        .requestMatchers("/v1/auth/refresh-token").authenticated()
                        .requestMatchers(HttpMethod.GET, "/v1/books", "/v1/books/{id}").permitAll()

                        .requestMatchers(HttpMethod.POST, "/v1/books/{id}/request-loan").authenticated()
                        .requestMatchers(HttpMethod.GET, "/v1/books/requests/my-requests").authenticated()

                        .requestMatchers(HttpMethod.POST, "/v1/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v1/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/books/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/v1/books/requests/{requestId}/approve")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(HttpMethod.POST, "/v1/books/requests/{requestId}/reject")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(HttpMethod.POST, "/v1/books/requests/{requestId}/return")
                        .hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/v1/books/requests/pending").hasAnyRole("ADMIN", "LIBRARIAN")

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
