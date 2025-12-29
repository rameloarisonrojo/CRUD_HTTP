package com.example.demo_bibliotheque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactivé pour faciliter les tests API (Postman)
            .authorizeHttpRequests(auth -> auth
                // 1. Tout le monde peut voir la liste (GET)
                .requestMatchers(HttpMethod.GET, "/api/v1/livres", "/api/v1/livres/**").permitAll()
                // 2. Il faut être ADMIN pour créer, modifier ou supprimer
                .requestMatchers("/api/v1/livres", "/api/v1/livres/**").hasRole("ADMIN")
                // 3. Permettre l'accès à la console H2 (très important pour vous !)
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Nécessaire pour H2
            .httpBasic(Customizer.withDefaults()); // Utilise l'authentification basique (Login/Pass)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // On crée un utilisateur en mémoire pour le test
        UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}password123") // {noop} signifie "pas d'encodage" pour ce test simple
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }
}