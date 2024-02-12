package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.seguridad.Usuario;
import java.util.List;

// Interfaz para operaciones de base de datos relacionadas con usuarios
public interface UsuarioRepository {
    // Guarda o actualiza un usuario en la base de datos
    Usuario save(Usuario usuario);
    // Encuentra un usuario por su ID
    Usuario findById(Long id);
    // Encuentra un usuario por su correo electrónico
    Usuario findByEmail(String email);
    // Elimina un usuario de la base de datos por su ID
    void deleteById(Long id);
    // Lista todos los usuarios registrados
    List<Usuario> findAll();
    // Métodos adicionales para búsqueda por rol, etc.
}
