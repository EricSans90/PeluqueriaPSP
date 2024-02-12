package PeluqueriaPSP.seguridad;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;

// Clase para definir constantes usadas en la configuración de seguridad y JWT
public class Constans {

    // Spring Security
    public static final String LOGIN_URL = "/usuarios/login";
    public static final String SIGNUP_URL = "/usuarios/signup";
    public static final String HEADER_AUTHORIZACION_KEY = "token";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

    // Clave secreta y vector de inicialización para AES
    public static final String SECRET_KEY = "1234567890123456"; // Clave secreta para AES (16, 24 o 32 bytes)
    public static final String INIT_VECTOR = "1234567890123456"; // Vector de inicialización (16 bytes)

    // Genera una clave de firma para JWT basada en la llave secreta
    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
