package PeluqueriaPSP.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder passwordEncoder;

    // Constructor para inyección de dependencias
    @Autowired
    public WebSecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter, UserDetailsService userDetailsService,
                             AuthenticationConfiguration authenticationConfiguration, PasswordEncoder passwordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // configura la seguridad HTTP, se ven las rutas sin autenticación (/login y /signup) y las otras con
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authz -> authz
                    // Permite acceso sin autenticación a los endpoints de login y registro
                    .requestMatchers(HttpMethod.POST,Constans.LOGIN_URL).permitAll()
                    .requestMatchers(HttpMethod.POST,Constans.SIGNUP_URL).permitAll()
                    // Endpoints para el Administrador
                    // Puede gestionar peluqueros y acceder a todas las operaciones relacionadas con /peluqueros/**
                    .requestMatchers(HttpMethod.POST, "/peluqueros").hasAuthority("ROLE_ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/peluqueros/{id}").hasAuthority("ROLE_ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/peluqueros/{id}").hasAuthority("ROLE_ADMINISTRADOR")
                    .requestMatchers(HttpMethod.GET, "/peluqueros/{id}", "/peluqueros").hasAuthority("ROLE_ADMINISTRADOR")
                    // Endpoints para el Peluquero
                    // Puede gestionar turnos (crear, eliminar, actualizar sus propios turnos y ver todos sus turnos)
                    .requestMatchers(HttpMethod.POST, "/turnos").hasAuthority("ROLE_PELUQUERO")
                    .requestMatchers(HttpMethod.DELETE, "/turnos/{id}").hasAuthority("ROLE_PELUQUERO")
                    .requestMatchers(HttpMethod.PUT, "/turnos/{id}").hasAuthority("ROLE_PELUQUERO")
                    .requestMatchers(HttpMethod.GET, "/turnos/misTurnos").hasAuthority("ROLE_PELUQUERO")
                    // Endpoints accesibles para todos los roles registrados
                    // Para ver todos los turnos no necesitamos rol
                    .requestMatchers(HttpMethod.GET, "/turnos").permitAll()
                    // Endpoints para el Cliente
                    // Puede gestionar reservas (crear una reserva para un turno, cancelar sus reservas y ver sus reservas)
                    .requestMatchers(HttpMethod.POST, "/reservas/turnos/{idTurno}/reservar").hasAuthority("ROLE_CLIENTE")
                    .requestMatchers(HttpMethod.DELETE, "/reservas/{idReserva}").hasAuthority("ROLE_CLIENTE")
                    .requestMatchers(HttpMethod.GET, "/reservas/misReservas", "/reservas/{id}").hasAuthority("ROLE_CLIENTE")
                    // Endpoints para Administradores en el control de roles (si as implementase en el futuro)
                    .requestMatchers(HttpMethod.GET, "/roles", "/roles/{id}").hasAuthority("ROLE_ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLE_ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/roles/{id}").hasAuthority("ROLE_ADMINISTRADOR")
                .anyRequest().authenticated())
                     //asegurar que las peticiones sean verificadas para un token JWT válido
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Se configura globalmente con el UserDetailsService y el PasswordEncoder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}