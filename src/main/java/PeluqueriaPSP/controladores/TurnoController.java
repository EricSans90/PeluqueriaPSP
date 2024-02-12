package PeluqueriaPSP.controladores;

import PeluqueriaPSP.entidades.Peluquero;
import PeluqueriaPSP.entidades.Turno;
import PeluqueriaPSP.seguridad.Usuario;
import PeluqueriaPSP.servicios.TurnoService;
import PeluqueriaPSP.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador para gestionar acciones relacionadas con los turnos de los peluqueros
@RestController
@RequestMapping("/turnos")
public class TurnoController {
    // Inyección de dependencias para acceder a los servicios de usuario y turno
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private UsuarioService usuarioService;

    // ● Endpoint para Crear un nuevo turno (nombre, fecha, hora, duración, reservado (true o false) peluquero que realizará el corte)
    @PostMapping
    // Solo accesible por peluqueros
    @PreAuthorize("hasAuthority('ROLE_PELUQUERO')")
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
        // Aquí, asumiendo que el ID del peluquero ya está establecido en el turno a través del cuerpo de la solicitud.
        Turno nuevoTurno = turnoService.crearTurno(turno);
        return ResponseEntity.ok(nuevoTurno);
    }

    // ● Endpoint para Borrar un turno
    @DeleteMapping("/{id}")
    // Solo accesible por peluqueros
    @PreAuthorize("hasAuthority('ROLE_PELUQUERO')")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok().build();
    }

    // ● Endpoint para Actualizar la información del turno
    @PutMapping("/{id}")
    // Solo accesible por peluqueros
    @PreAuthorize("hasAuthority('ROLE_PELUQUERO')")
    public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
        Turno turnoActualizado = turnoService.actualizarTurno(id, turno);
        if (turnoActualizado != null) {
            return ResponseEntity.ok(turnoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ● Endpoint para Listar todos los turnos que ha creado él (peluquero)
    @GetMapping("/misTurnos")
    // Solo accesible por peluqueros
    @PreAuthorize("hasAuthority('ROLE_PELUQUERO')")
    public List<Turno> listarMisTurnos() {
        // OBtengo el ID del peluquero
        Long peluqueroIdActual = obtenerPeluqueroIdDeSeguridad();
        return turnoService.listarTurnosPorPeluquero(peluqueroIdActual);
    }

    // ● Endpoint para Listar todos los turnos que hay creados.
    @GetMapping
    public List<Turno> listarTodosLosTurnos() {
        return turnoService.listarTodosLosTurnos();
    }

    // ● Endpoint para Consultar información de un turno en concreto.
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) {
        Turno turno = turnoService.buscarTurnoPorId(id);
        if (turno != null) {
            return ResponseEntity.ok(turno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método auxiliar para obtener el ID del peluquero actual desde el contexto de seguridad
    private Long obtenerPeluqueroIdDeSeguridad() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        Peluquero peluquero = usuarioService.encontrarPeluqueroPorUsuarioId(usuario.getIdUsuario());
        return peluquero.getIdPeluquero();
    }
}
