package PetAppBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF nije potreban za REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login","/api/pets/**","/api/pet-types","/api/**").permitAll()
                        .requestMatchers("/home","/pets/**","/appointments/**").authenticated()// Dozvoli bez autentifikacije
                        .anyRequest().authenticated()

                        // Sve ostalo zahteva autentifikaciju
                )

                .httpBasic(Customizer.withDefaults()); // Koristi HTTP Basic autentifikaciju za API

        return http.build();
    }
    }



