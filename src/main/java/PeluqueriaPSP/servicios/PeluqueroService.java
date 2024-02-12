package PeluqueriaPSP.servicios;
import PeluqueriaPSP.entidades.Peluquero;
import PeluqueriaPSP.repositorios.PeluqueroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para gestionar operaciones relacionadas con los peluqueros
@Service
public class PeluqueroService {

    // Inyección de dependencias para acceder al repositorio peluquero
    @Autowired
    private PeluqueroRepository peluqueroRepository;

    // ● Crear un nuevo peluquero (nombre, fecha nacimiento, DNI)
    public Peluquero guardarPeluquero(Peluquero peluquero) {
        return peluqueroRepository.guardarPeluquero(peluquero);
    }

    // ● Borrar un peluquero por su ID
    public void eliminarPeluquero(Long id) {
        peluqueroRepository.borrarPeluqueroPorId(id);
    }

    // ● Actualizar la información de un peluquero existente
    public Peluquero actualizarPeluquero(Long id, Peluquero peluqueroActualizado) {
        Peluquero peluqueroExistente = peluqueroRepository.encontrarPeluqueroPorId(id);
        if (peluqueroExistente == null) {
            throw new EntityNotFoundException("Peluquero no encontrado con el ID: " + id);
        }
        // Actualizar campos en peluqueroExistente con valores de peluqueroActualizado
        peluqueroExistente.setNombre(peluqueroActualizado.getNombre());
        peluqueroExistente.setFechaNacimiento(peluqueroActualizado.getFechaNacimiento());
        peluqueroExistente.setDni(peluqueroActualizado.getDni());

        // Guardar el peluquero actualizado en la base de datos
        return peluqueroRepository.guardarPeluquero(peluqueroExistente);
    }

    // ● Consultar la información de un peluquero por su ID
    public Peluquero buscarPorId(Long id) {
        Peluquero peluquero = peluqueroRepository.encontrarPeluqueroPorId(id);
        if (peluquero == null) {
            throw new EntityNotFoundException("Peluquero no encontrado con el ID: " + id);
        }
        return peluquero;
    }

    // ● Consultar la información de todos los peluqueros
    public List<Peluquero> listarTodos() {
        return peluqueroRepository.todosPeluqueros();
    }
}