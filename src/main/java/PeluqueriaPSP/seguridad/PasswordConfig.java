package PeluqueriaPSP.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

// Configuración para la codificación de contraseñas
@Configuration
public class PasswordConfig {

    // Bean para codificar y validar contraseñas usando un encriptador personalizado (PasswordEncryptor)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return PasswordEncryptor.encrypt(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String decrypted = PasswordEncryptor.decrypt(encodedPassword);
                return decrypted != null && decrypted.equals(rawPassword.toString());
            }
        };
    }
}