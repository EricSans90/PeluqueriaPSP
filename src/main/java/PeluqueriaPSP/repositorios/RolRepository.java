package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.seguridad.Rol;
import java.util.List;

// Interfaz para operaciones de base de datos relacionadas con los roles
public interface RolRepository {
    // Guarda un nuevo rol o actualiza uno existente
    Rol save(Rol rol);
    // Encuentra un rol por su ID
    Rol findById(Integer id);
    // Encuentra un rol por su nombre
    Rol findByNombreRol(String nombreRol);
    // Elimina un rol por su ID
    void deleteById(Integer id);
    // Lista todos los roles disponibles
    List<Rol> findAll();
}
