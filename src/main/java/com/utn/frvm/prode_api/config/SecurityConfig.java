package com.utn.frvm.prode_api.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ── Sin estado (REST) ──────────────────────────────────────────
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // ── Autorización ───────────────────────────────────────────────
            .authorizeHttpRequests(auth -> auth
                // frontend
                .requestMatchers("/", "/index.html").permitAll()
                .requestMatchers("/*css", "/*js", "/*images", "/static/**").permitAll()

                // auth pública
                .requestMatchers("/api/auth/**").permitAll()

                // datos públicos
                .requestMatchers(HttpMethod.GET, "/api/equipos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/jornadas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/partidos/**").permitAll()
                .requestMatchers("/api/reglas/**").permitAll()

                // admin
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            // ── Respuestas de error en formato JSON ────────────────────────
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(
                        "{\"exito\":false," +
                        "\"mensaje\":\"No autorizado. Inicia sesion nuevamente.\"," +
                        "\"datos\":null}"
                    );
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(
                        "{\"exito\":false," +
                        "\"mensaje\":\"Acceso denegado. No tienes permisos suficientes.\"," +
                        "\"datos\":null}"
                    );
                })
            )

            // ── Proveedor de autenticación ─────────────────────────────────
            .authenticationProvider(authenticationProvider())

            // ── Filtro JWT antes del filtro estándar de usuario/contraseña ─
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proveedor que valida credenciales usando UserDetailsService + PasswordEncoder.
     * (Es utilizado internamente por Spring Security, no por el filtro JWT.)
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Expone el AuthenticationManager como bean para poder inyectarlo en AuthService
     * si algún día se necesita autenticar programáticamente.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
