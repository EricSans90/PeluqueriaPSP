package PeluqueriaPSP.seguridad;

import PeluqueriaPSP.repositorios.RolRepository;
import PeluqueriaPSP.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

// Servicio para cargar los detalles de un usuario durante la autenticación.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Inyección de dependencias para acceder a los repositorios usuario y rol
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    // Carga un usuario por su correo electrónico y lo convierte a un UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Búsqueda del usuario y su rol, y conversión a UserDetails
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        Rol rol = rolRepository.findById(usuario.getIdRol());
        if (rol == null) {
            throw new UsernameNotFoundException("Rol no encontrado para el usuario: " + email);
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContrasenya())
                // Uso SimpleGrantedAuthority para asignar roles
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol())))
                .build();
    }
}
