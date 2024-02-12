package PeluqueriaPSP.servicios;

import PeluqueriaPSP.entidades.Reserva;
import PeluqueriaPSP.entidades.Turno;
import PeluqueriaPSP.repositorios.ReservaRepository;
import PeluqueriaPSP.repositorios.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para gestionar operaciones relacionadas con las reservas
@Service
public class ReservaService {

    @Autowired
    // Añade la inyección de ReservaRepository
    private ReservaRepository reservaRepository;
    @Autowired
    // Añade la inyección de TurnoRepository
    private TurnoRepository turnoRepository;

    // ● Apuntarse a un turno (modificará reservado de turno). Es = que guardar una reserva.
    public Reserva apuntarseATurno(Long idTurno, Long idUsuario) {
        // Obtener turno por ID
        Turno turno = turnoRepository.findById(idTurno);
        if (turno == null || turno.getReservado()) {
            throw new IllegalStateException("El turno no está disponible.");
        }
        turno.setReservado(true);
        // Actualizar el turno a reservado
        turnoRepository.save(turno);
        // Crear y guardar la reserva
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(idUsuario);
        reserva.setIdTurno(idTurno);
        return reservaRepository.save(reserva);
    }
    // ● Listar todos los turnos que hay creados.
    public List<Reserva> listarTodasLasReservas() {
        return reservaRepository.findAll();
    }

    // ● Listar todos los turnos a los que se ha apuntado (usuario actual)
    public List<Reserva> listarReservasPorUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    // Busca una reserva específica por su ID
    public Reserva buscarReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // ● Desapuntarse (borrar) de un turno al que se haya apuntado.
    public void cancelarReserva(Long idReserva, Long idUsuario) {
        // Encuentra la reserva por ID
        Reserva reserva = reservaRepository.findById(idReserva);
        if (reserva == null || !reserva.getIdUsuario().equals(idUsuario)) {
            throw new IllegalStateException("Operación no permitida o reserva no encontrada.");
        }
        // Elimina la reserva
        reservaRepository.deleteById(idReserva);
        // Encuentra el turno asociado a la reserva y cambia su estado a no reservado
        Turno turno = turnoRepository.findById(reserva.getIdTurno());
        if (turno != null) {
            // Cambia el estado del turno a no reservado
            turno.setReservado(false);
            // Guarda los cambios en el turno
            turnoRepository.save(turno);
        } else {
            throw new IllegalStateException("Turno asociado a la reserva no encontrado.");
        }
    }
}