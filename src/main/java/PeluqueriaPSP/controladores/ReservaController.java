package PeluqueriaPSP.controladores;

import PeluqueriaPSP.entidades.Reserva;
import PeluqueriaPSP.seguridad.Usuario;
import PeluqueriaPSP.servicios.ReservaService;
import PeluqueriaPSP.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador para gestionar acciones relacionadas con las reservas de turnos
@RestController
@RequestMapping("/reservas")
public class ReservaController {
    // Inyección de dependencias para acceder a los servicios de reserva y usuario
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UsuarioService usuarioService;

    // ● Endpoint para Crear una reserva, apuntarse a un turno (modificará reservado de turno).
    @PostMapping("turnos/{idTurno}/reservar")
    // Solo accesible por clientes
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<Reserva> reservarTurno(@PathVariable Long idTurno) {
        // Obtengo el ID del usuario autenticado
        Long idUsuario = obtenerUsuarioActualId();
        Reserva reserva = reservaService.apuntarseATurno(idTurno, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    // ● Endpoint para Listar todos los turnos que hay creados.
    @GetMapping
    public List<Reserva> listarTodasLasReservas() {
        return reservaService.listarTodasLasReservas();
    }


    // ● Endpoint para Listar todos los turnos a los que se ha apuntado (usuario)
    @GetMapping("/misReservas")
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<List<Reserva>> misReservas() {
        // Obtengo el ID del usuario autenticado
        Long idUsuario = obtenerUsuarioActualId();
        List<Reserva> reservas = reservaService.listarReservasPorUsuarioId(idUsuario);
        return ResponseEntity.ok(reservas);
    }

    //FALTA A DOCUMENTACION
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarReservaPorId(id);
        if (reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ● Endpoint para Desapuntarse (borrar) de un turno al que se haya apuntado.
    @DeleteMapping("{idReserva}")
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long idReserva) {
        // Obtengo el ID del usuario autenticado
        Long idUsuario = obtenerUsuarioActualId();
        reservaService.cancelarReserva(idReserva, idUsuario);
        return ResponseEntity.ok().build();
    }

    // Obtengo el email del usuario autenticado y luego busco su ID en la BD y lo devuelvo
    private Long obtenerUsuarioActualId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return usuario.getIdUsuario();
    }
}