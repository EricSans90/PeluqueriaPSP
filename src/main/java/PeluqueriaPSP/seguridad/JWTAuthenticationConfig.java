package PeluqueriaPSP.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.Date;
import java.util.List;
import static PeluqueriaPSP.seguridad.Constans.*;

// Configuración para la generación de tokens JWT
@Configuration
public class JWTAuthenticationConfig {

    public String getJWTToken(String username, String rol) {
        // Genera un token JWT para un usuario con un rol específic
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + rol);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("rol", rol)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();

        return TOKEN_BEARER_PREFIX + token;
    }
}