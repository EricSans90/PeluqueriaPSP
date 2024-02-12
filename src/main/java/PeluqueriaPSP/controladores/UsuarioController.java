package PeluqueriaPSP.controladores;

import PeluqueriaPSP.seguridad.JWTAuthenticationConfig;
import PeluqueriaPSP.seguridad.Rol;
import PeluqueriaPSP.seguridad.Usuario;
import PeluqueriaPSP.servicios.RolService;
import PeluqueriaPSP.servicios.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador para gestionar acciones relacionadas con los usuarios,
// incluyendo autenticación y administración de usuarios
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    // Inyección de dependencias para acceder a los servicios de peluquero y rol y
    // para acceder a jwtAuthenticationConfig y authenticationManager
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RolService rolService;

    // Endpoint para registrar un nuevo usuario en el sistema (abierto a todos)
    @PostMapping("/signup")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    // Endpoint para hacer login = autenticar un usuario y generar un token JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getContrasenya())
        );
        String token = jwtAuthenticationConfig.getJWTToken(authentication.getName(), obtenerRoles(authentication));
        return ResponseEntity.ok().header("Authorization", token).body("Token generado");
    }

    // Endpoint para listar todos los usuarios
    @GetMapping
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    // Endpoint para Buscar usuario por ID
    @GetMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar usuario
    @DeleteMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

    // Método auxiliar para obtener los roles del usuario autenticado
    private String obtenerRoles(Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + authentication.getName());
        }
        Rol rol = rolService.buscarPorId(usuario.getIdRol());
        if (rol == null) {
            throw new EntityNotFoundException("Rol no encontrado para el usuario: " + authentication.getName());
        }
        return rol.getNombreRol();
    }
}