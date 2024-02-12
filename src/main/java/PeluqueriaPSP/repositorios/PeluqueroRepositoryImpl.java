package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Peluquero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

// Implementación concreta de PeluqueroRepository
@Repository
public class PeluqueroRepositoryImpl implements PeluqueroRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor para inyección de JdbcTemplate
    @Autowired
    public PeluqueroRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Mapea cada fila de resultado a un objeto Peluquero
    private final RowMapper<Peluquero> rowMapper = (rs, rowNum) -> {
        Peluquero peluquero = new Peluquero();
        peluquero.setIdPeluquero(rs.getLong("id_peluquero"));
        peluquero.setIdUsuario(rs.getLong("id_usuario"));
        peluquero.setDni(rs.getString("dni"));
        peluquero.setNombre(rs.getString("nombre"));
        peluquero.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        return peluquero;
    };

    // ● Crear un nuevo peluquero (nombre, fecha nacimiento, DNI)
    // ● Actualizar la información de un peluquero
    @Override
    public Peluquero guardarPeluquero(Peluquero peluquero) {
        if (peluquero.getIdPeluquero() == null) {
            String sql = "INSERT INTO Peluqueros (id_usuario, dni, nombre, fecha_nacimiento) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, peluquero.getIdUsuario(), peluquero.getDni(), peluquero.getNombre(),
                    new java.sql.Date(peluquero.getFechaNacimiento().getTime()));
        } else {
            String sql = "UPDATE Peluqueros SET id_usuario = ?, dni = ?, nombre = ?, fecha_nacimiento = ? WHERE id_peluquero = ?";
            jdbcTemplate.update(sql, peluquero.getIdUsuario(), peluquero.getDni(), peluquero.getNombre(),
                    new java.sql.Date(peluquero.getFechaNacimiento().getTime()), peluquero.getIdPeluquero());
        }
        return peluquero;
    }
    // ● Borrar un peluquero
    @Override
    public void borrarPeluqueroPorId(Long id) {
        String sql = "DELETE FROM Peluqueros WHERE id_peluquero = ?";
        jdbcTemplate.update(sql, id);
    }
    // ● Consultar la información de todos los peluqueros
    @Override
    public List<Peluquero> todosPeluqueros() {
        String sql = "SELECT * FROM Peluqueros";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // ● Consultar la información de un peluquero
    @Override
    public Peluquero encontrarPeluqueroPorId(Long id) {
        String sql = "SELECT * FROM Peluqueros WHERE id_peluquero = ?";
        List<Peluquero> peluqueros = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        return peluqueros.isEmpty() ? null : peluqueros.get(0);
    }

    // buscar un peluquero por el ID de usuario asociado
    @Override
    public Peluquero encontrarPeluqueroPorUsuarioId(Long idUsuario) {
        String sql = "SELECT * FROM Peluqueros WHERE id_usuario = ?";
        List<Peluquero> peluqueros = jdbcTemplate.query(sql, new Object[]{idUsuario}, rowMapper);
        return peluqueros.isEmpty() ? null : peluqueros.get(0);
    }
}