package PeluqueriaPSP.servicios;

import PeluqueriaPSP.entidades.Peluquero;
import PeluqueriaPSP.repositorios.PeluqueroRepository;
import PeluqueriaPSP.repositorios.RolRepository;
import PeluqueriaPSP.repositorios.UsuarioRepository;
import PeluqueriaPSP.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para gestionar operaciones relacionadas con los usuarios
@Service
public class UsuarioService {
    // Inyección de dependencias para acceder a los repositorios usuario rol y peluquero
    // además de al passwordEncoder para el cifrado de contraseñas
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PeluqueroRepository peluqueroRepository;

    // Lista todos los usuarios registrados
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Busca un usuario por su ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Busca un usuario por su email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Registra un nuevo usuario en el sistema
    public Usuario registrarUsuario(Usuario usuario) {
        if(usuario.getIdRol() == null) {
            // Asignación de rol predeterminado a CLIENTE
            usuario.setIdRol(3);
        }
        usuario.setContrasenya(passwordEncoder.encode(usuario.getContrasenya()));
        return usuarioRepository.save(usuario);
    }

    // Elimina un usuario por su ID
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Encuentra el peluquero asociado a un usuario específico
    public Peluquero encontrarPeluqueroPorUsuarioId(Long idUsuario) {
        return peluqueroRepository.encontrarPeluqueroPorUsuarioId(idUsuario);
    }

}