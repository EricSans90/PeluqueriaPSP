package PeluqueriaPSP.servicios;

import PeluqueriaPSP.repositorios.RolRepository;
import PeluqueriaPSP.seguridad.Rol;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para gestionar operaciones relacionadas con los roles de usuarios
@Service
public class RolService {

    // Inyección de dependencias para acceder al repositorio rol
    @Autowired
    private RolRepository rolRepository;

    // Lista todos los roles disponibles
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    // Busca un rol por su ID
    public Rol buscarPorId(Integer id) {
        Rol rol = rolRepository.findById(id);
        if (rol == null) {
            throw new EntityNotFoundException("Rol no encontrado con ID: " + id);
        }
        return rol;
    }

    // Guarda un nuevo rol o actualiza uno existente
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Elimina un rol por su ID
    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }

}
