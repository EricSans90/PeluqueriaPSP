package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.List;

// Implementación del repositorio para la entidad Usuario
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor para inyección de JdbcTemplate
    @Autowired
    public UsuarioRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapea cada fila del resultado SQL a un objeto Usuario
    private RowMapper<Usuario> rowMapper = (ResultSet rs, int rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getLong("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setContrasenya(rs.getString("contrasenya"));
        usuario.setIdRol(rs.getInt("id_rol"));
        return usuario;
    };

    // Implementación para guardar o actualizar un usuario
    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            String sql = "INSERT INTO  Usuarios " +
                    "(idUsuario, nombre, email, contrasenya, idRol) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, usuario.getIdUsuario(), usuario.getNombre(),
                    usuario.getEmail(), usuario.getIdRol());
        } else {
            String sql = "UPDATE Usuarios " +
                    "(idUsuario, nombre, email, contrasenya, idRol) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, usuario.getIdUsuario(), usuario.getNombre(),
                    usuario.getEmail(), usuario.getIdRol());
        }
        return usuario;
    }

    // Implementación para encontrar un usuario por su ID
    @Override
    public Usuario findById(Long id) {
        String sql = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper)
                .stream()
                .findFirst()
                .orElse(null); // Retorna null si no encuentra ningún usuario
    }

    // Implementación para encontrar un usuario por su correo electrónico.
    @Override
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, rowMapper)
                .stream()
                .findFirst()
                .orElse(null); // Retorna null si no encuentra ningún usuario
    }

    // Implementación para eliminar un usuario por su ID
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id);
    }

    // Implementación para listar todos los usuarios registrados
    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM Usuarios";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
