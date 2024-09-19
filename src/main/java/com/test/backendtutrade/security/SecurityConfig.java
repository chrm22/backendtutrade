package com.test.backendtutrade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private static final String[] AUTH_WHITELIST = {
            // -- login
            "/api/usuarios/login/**",
            "/api/usuarios/register/**"
    };

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());

        http.authorizeHttpRequests((auth) -> auth

                        .requestMatchers(AUTH_WHITELIST).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyAuthority("ROL_USUARIO", "ROL_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/articulos/**").hasAnyAuthority("ROL_USUARIO", "ROL_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/valoraciones/**").hasAnyAuthority("ROL_USUARIO","ROL_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pedidos/**").hasAuthority("ROL_USUARIO")

                        .requestMatchers(HttpMethod.GET, "/api/conteo/estado").hasAuthority("ROL_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/articulos/**").hasAuthority("ROL_USUARIO")
                        .requestMatchers(HttpMethod.POST, "/api/valoraciones/**").hasAuthority("ROL_USUARIO")
//
                        .requestMatchers(HttpMethod.PUT, "/api/pedidos/**").hasAnyAuthority("ROL_USUARIO","ROL_ADMIN")
//                        //.antMatchers(HttpMethod.GET,"/api/employees").hasAnyRole("STUDENT", "ADMIN")ROLE_UNIVERSITARIO
//
//                        .requestMatchers("/api/estudiantes/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
        );

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
