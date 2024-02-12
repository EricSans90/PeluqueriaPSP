package PeluqueriaPSP.servicios;

import PeluqueriaPSP.entidades.Turno;
import PeluqueriaPSP.repositorios.TurnoRepositoryImpl;
import PeluqueriaPSP.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para gestionar operaciones relacionadas con los turnos de los peluqueros.
@Service
public class TurnoService {
    // Inyección de dependencias para acceder a los repositorios de turno y usuario
    @Autowired
    private TurnoRepositoryImpl turnoRepository;
    @Autowired
    private UsuarioService usuarioService;

    // Método para obtener la ID del peluquero ya autenticado
    private Long obtenerIdPeluqueroAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        // Este ID debe corresponder al ID del peluquero
        return usuario.getIdUsuario();
    }

    // ● Crear un nuevo turno (nombre, fecha, hora, duración, reservado (true o false) peluquero que realizará el corte)
    public Turno crearTurno(Turno turno) {
        Long idPeluquero = obtenerIdPeluqueroAutenticado();
        turno.setIdPeluquero(idPeluquero);
        return turnoRepository.save(turno);
    }

    // ● Borrar un turno verificando que pertenezca al peluquero autenticado
    public void eliminarTurno(Long id) {
        Long idPeluquero = obtenerIdPeluqueroAutenticado();
        Turno turno = turnoRepository.findById(id);
        if (!turno.getIdPeluquero().equals(idPeluquero)) {
            throw new SecurityException("No autorizado");
        }
        turnoRepository.deleteById(id);
    }

    // ● Actualizar la información del turno
    public Turno actualizarTurno(Long id, Turno turnoDetalles) {
        Long idPeluquero = obtenerIdPeluqueroAutenticado();
        Turno turno = turnoRepository.findById(id);
        // Si el peluquero no correposponde a esa id y por tanto no puede hacer el cambio
        if (!turno.getIdPeluquero().equals(idPeluquero)) {
            throw new SecurityException("No autorizado");
        }
        turno.setNombre(turnoDetalles.getNombre());
        turno.setFecha(turnoDetalles.getFecha());
        turno.setHora(turnoDetalles.getHora());
        turno.setDuracion(turnoDetalles.getDuracion());
        turno.setReservado(turnoDetalles.getReservado());

        return turnoRepository.save(turno);
    }
    // ● Listar todos los turnos que ha creado él (peluquero)
    // le paso el ID del peluquero autenticado como argumento
    public List<Turno> listarTurnosPorPeluquero(Long idPeluquero) {
        return turnoRepository.findByPeluqueroId(idPeluquero);
    }
    // ● Listar todos los turnos que hay creados.
    public List<Turno> listarTodosLosTurnos() {
        return turnoRepository.findAll();
    }

    // ● Consultar información de un turno en concreto.
    public Turno buscarTurnoPorId(Long id) {
        return turnoRepository.findById(id);
    }
}
