package com.xiplatani.viajes.libreria.infrastructure.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.xiplatani.viajes.libreria.infrastructure.security.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${client.url}")
    private String CLIENT_URL;

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/api/auth/register", "/v1/api/auth/login", "/v1/api/seed").permitAll()
                        .requestMatchers("/v1/api/auth/refresh-token").authenticated()
                        .requestMatchers("/v1/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/v1/api/books", "/v1/api/books/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/{id}/request-loan").authenticated()
                        .requestMatchers(HttpMethod.GET, "/v1/api/books/requests/my-requests").authenticated()
                        .requestMatchers(HttpMethod.POST, "/v1/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v1/api/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/api/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/approve").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/reject").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/v1/api/books/requests/{requestId}/return").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/v1/api/books/requests", "/v1/api/books/requests/pending").hasAnyRole("ADMIN", "LIBRARIAN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(this.CLIENT_URL));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
