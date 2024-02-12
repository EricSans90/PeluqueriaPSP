package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

// Implementación concreta de ReservaRepository
@Repository
public class ReservaRepositoryImpl implements ReservaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapeador de filas que convierte una fila del resultado SQL en un objeto Reserva
    private RowMapper<Reserva> rowMapper = (rs, rowNum) -> {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getLong("id_reserva"));
        reserva.setIdUsuario(rs.getLong("id_usuario"));
        reserva.setIdTurno(rs.getLong("id_turno"));
        return reserva;
    };

    // Guarda una nueva reserva o actualiza una existente en la base de datos
    @Override
    public Reserva save(Reserva reserva) {
        if (reserva.getIdReserva() == null) {
            String sql = "INSERT INTO Reservas (id_usuario, id_turno) VALUES (?, ?)";
            jdbcTemplate.update(sql, reserva.getIdUsuario(), reserva.getIdTurno());
        } else {
            String sql = "UPDATE Reservas SET id_usuario = ?, id_turno = ? WHERE id_reserva = ?";
            jdbcTemplate.update(sql, reserva.getIdUsuario(), reserva.getIdTurno(), reserva.getIdReserva());
        }
        return reserva;
    }

    // Encuentra una reserva por su ID
    @Override
    public Reserva findById(Long id) {
        List<Reserva> reservas = jdbcTemplate.query("SELECT * FROM Reservas WHERE id_reserva = ?", new Object[]{id}, rowMapper);
        return reservas.isEmpty() ? null : reservas.get(0);
    }

    // Elimina una reserva de la base de datos por su ID
    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Reservas WHERE id_reserva = ?", id);
    }

    // Lista todas las reservas existentes en la base de datos
    @Override
    public List<Reserva> findAll() {
        return jdbcTemplate.query("SELECT * FROM Reservas", rowMapper);
    }

    // Encuentra todas las reservas asociadas a un ID de usuario específico.
    @Override
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return jdbcTemplate.query("SELECT * FROM Reservas WHERE id_usuario = ?", new Object[]{usuarioId}, rowMapper);
    }
}