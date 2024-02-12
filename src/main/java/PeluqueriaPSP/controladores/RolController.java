package PeluqueriaPSP.controladores;

import PeluqueriaPSP.seguridad.Rol;
import PeluqueriaPSP.servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador para gestionar acciones relacionadas con los roles de los usuarios
// No lo pide el enunciado pero podrían ser interesantes de implementarse adecuadamente en el futuro
@RestController
@RequestMapping("/roles")
public class RolController {
    // Inyección de dependencias para acceder a los servicios de rol
    @Autowired
    private RolService rolService;

    // Endpoint para listar todos los roles disponibles en el sistema
    @GetMapping
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public List<Rol> listarTodos() {
        return rolService.listarTodos();
    }

    // Endpoint para buscar un rol por su ID
    @GetMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Integer id) {
        Rol rol = rolService.buscarPorId(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint para crear o actualizar un rol
    @PostMapping
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        Rol rolGuardado = rolService.guardarRol(rol);
        return ResponseEntity.ok(rolGuardado);
    }

    // Endpoint para eliminar un rol por su ID
    @DeleteMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return ResponseEntity.ok().build();
    }

}
