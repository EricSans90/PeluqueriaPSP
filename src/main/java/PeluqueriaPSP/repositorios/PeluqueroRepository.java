package PeluqueriaPSP.repositorios;

import PeluqueriaPSP.entidades.Peluquero;
import java.util.List;

public interface PeluqueroRepository {
    // Guarda un peluquero en la base de datos, actualiza si ya existe
    Peluquero guardarPeluquero(Peluquero peluquero);
    // Encuentra un peluquero por su ID
    Peluquero encontrarPeluqueroPorId(Long id);
    // Elimina un peluquero de la base de datos por su ID
    void borrarPeluqueroPorId(Long id);
    // Devuelve una lista de todos los peluqueros en la base de datos
    List<Peluquero> todosPeluqueros();
    // Encuentra un peluquero por el ID de usuario asociado
    Peluquero encontrarPeluqueroPorUsuarioId(Long idUsuario);
    //si hacen falta más lso pongo aquí
}
