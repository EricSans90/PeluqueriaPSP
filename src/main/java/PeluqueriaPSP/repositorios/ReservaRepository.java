package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Reserva;
import java.util.List;

// Interfaz para operaciones de base de datos relacionadas con las reservas
public interface ReservaRepository {
    // Guarda o actualiza una reserva en la base de datos
    Reserva save(Reserva reserva);
    // Encuentra una reserva por su ID
    Reserva findById(Long id);
    // Elimina una reserva por su ID
    void deleteById(Long id);
    // Lista todas las reservas existentes
    List<Reserva> findAll();
    // Encuentra todas las reservas hechas por un usuario específico
    List<Reserva> findByUsuarioId(Long usuarioId);
    // SI me hace falta algún método más lo pongo aquí
}
