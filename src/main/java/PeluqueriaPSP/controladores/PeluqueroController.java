package PeluqueriaPSP.controladores;

import PeluqueriaPSP.entidades.Peluquero;
import PeluqueriaPSP.servicios.PeluqueroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador para gestionar acciones relacionadas con los peluqueros
@RestController
@RequestMapping("/peluqueros")
public class PeluqueroController {

    // Inyección de dependencias para acceder a los servicios de peluquero
    @Autowired
    private PeluqueroService peluqueroService;

    // ● Endpoint para crear un nuevo peluquero (nombre, fecha nacimiento, DNI)
    @PostMapping
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public Peluquero guardarPeluquero(@RequestBody Peluquero peluquero) {
        return peluqueroService.guardarPeluquero(peluquero);
    }

    // ● Endpoint para borrar un peluquero
    @DeleteMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarPeluquero(@PathVariable Long id) {
        peluqueroService.eliminarPeluquero(id);
        return ResponseEntity.ok().build();
    }

    // ● Endpoint para actualizar la información de un peluquero
    @PutMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Peluquero> actualizarPeluquero(@PathVariable Long id, @RequestBody Peluquero peluquero) {
        Peluquero peluqueroActualizado = peluqueroService.actualizarPeluquero(id, peluquero);
        if(peluqueroActualizado != null) {
            return ResponseEntity.ok(peluqueroActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ● Endpoint para consultar la información de un peluquero
    @GetMapping("/{id}")
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Peluquero> buscarPorId(@PathVariable Long id) {
        Peluquero peluquero = peluqueroService.buscarPorId(id);
        if (peluquero != null) {
            return ResponseEntity.ok(peluquero);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ● Endpoint para consultar la información de todos los peluqueros
    @GetMapping
    // Solo accesible por administradores
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public List<Peluquero> listarTodos() {
        return peluqueroService.listarTodos();
    }

}