package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Turno;
import java.util.List;

// Interfaz para operaciones de base de datos relacionadas con turnos
public interface TurnoRepository {
    Turno save(Turno turno);
    Turno findById(Long id);
    void deleteById(Long id);
    List<Turno> findAll();
    List<Turno> findByPeluqueroId(Long peluqueroId);
    // Métodos adicionales para filtrar por estado de reserva, fecha, etc.
}
