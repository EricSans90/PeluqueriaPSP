package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;

// Implementación del repositorio para la entidad Turno
@Repository
public class TurnoRepositoryImpl implements TurnoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapea cada fila del resultado SQL a un objeto Turno
    private RowMapper<Turno> rowMapper = (rs, rowNum) -> {
        Turno turno = new Turno();
        turno.setIdTurno(rs.getLong("id_turno"));
        turno.setIdPeluquero(rs.getLong("id_peluquero"));
        turno.setNombre(rs.getString("nombre"));
        turno.setFecha(rs.getDate("fecha"));
        turno.setHora(rs.getTime("hora"));
        turno.setDuracion(rs.getInt("duracion"));
        turno.setReservado(rs.getBoolean("reservado"));
        return turno;
    };

    // Implementación para listar todos los turnos disponibles
    @Override
    public List<Turno> findAll() {
        String sql = "SELECT * FROM Turnos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Implementación para buscar un turno por su ID
    @Override
    public Turno findById(Long id) {
        List<Turno> turnos = jdbcTemplate.query("SELECT * FROM Turnos WHERE id_turno = ?", new Object[]{id}, rowMapper);
        return turnos.isEmpty() ? null : turnos.get(0);
    }

    // Implementación para guardar o actualizar un turno
    @Override
    public Turno save(Turno turno) {
        if (turno.getIdTurno() == null) {
            String sql = "INSERT INTO Turnos (id_peluquero, nombre, fecha, hora, duracion, reservado) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_turno"});
                ps.setLong(1, turno.getIdPeluquero());
                ps.setString(2, turno.getNombre());
                ps.setDate(3, new java.sql.Date(turno.getFecha().getTime()));
                ps.setTime(4, turno.getHora());
                ps.setInt(5, turno.getDuracion());
                ps.setBoolean(6, turno.getReservado());
                return ps;
            }, keyHolder);
            Number key = keyHolder.getKey();
            turno.setIdTurno(key != null ? key.longValue() : null);
        } else {
            String sql = "UPDATE Turnos SET id_peluquero = ?, nombre = ?, fecha = ?, hora = ?, duracion = ?, reservado = ? WHERE id_turno = ?";
            jdbcTemplate.update(sql,
                    turno.getIdPeluquero(),
                    turno.getNombre(),
                    new java.sql.Date(turno.getFecha().getTime()),
                    turno.getHora(),
                    turno.getDuracion(),
                    turno.getReservado(),
                    turno.getIdTurno());
        }
        return turno;
    }

    // Implementación para eliminar un turno por su ID
    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Turnos WHERE id_turno = ?", id);
    }

    // Implementación para buscar turnos por el ID de peluquero
    @Override
    public List<Turno> findByPeluqueroId(Long peluqueroId) {
        String sql = "SELECT t.*, p.nombre AS nombre_peluquero" +
                "FROM Turnos t" +
                "JOIN Peluqueros p ON t.id_peluquero = p.id_peluquero" +
                "WHERE p.id_peluquero = ?";
        return jdbcTemplate.query(sql, new Object[]{peluqueroId}, rowMapper);
    }
}
