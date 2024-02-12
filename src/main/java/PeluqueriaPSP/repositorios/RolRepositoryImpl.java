package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.seguridad.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

// Implementación concreta de RolRepository
@Repository
public class RolRepositoryImpl implements RolRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapeador de filas para convertir resultados SQL en objetos Rol
    private final RowMapper<Rol> rowMapper = (rs, rowNum) -> new Rol(
            rs.getInt("id_rol"),
            rs.getString("nombre_rol")
    );

    // Implementa la lógica para guardar o actualizar un rol
    @Override
    public Rol save(Rol rol) {
        // ME falta la implementación
        // De todos modos no está previsto añadir más roles de momento
        return rol;
    }

    // Encuentra un rol por su ID
    @Override
    public Rol findById(Integer id) {
        try {
            String sql = "SELECT * FROM Roles WHERE id_rol = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id},
                    (rs, rowNum) -> new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"))
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Encuentra un rol por su nombre
    @Override
    public Rol findByNombreRol(String nombreRol) {
        String sql = "SELECT * FROM Roles WHERE nombre_rol = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{nombreRol}, rowMapper);
    }

    // Elimina un rol de la base de datos por su ID
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Roles WHERE id_rol = ?";
        jdbcTemplate.update(sql, id);
    }

    // Lista todos los roles existentes en la base de datos
    @Override
    public List<Rol> findAll() {
        String sql = "SELECT * FROM Roles";
        return jdbcTemplate.query(sql, rowMapper);
    }
}