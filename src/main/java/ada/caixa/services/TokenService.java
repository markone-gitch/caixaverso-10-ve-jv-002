package ada.caixa.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class TokenService {
    public String gerarToken(
            String email,
            String role
    ) {
        return  Jwt.issuer("api-produtos")
                .subject(email)
                .groups(Set.of(role))
                .expiresIn(Duration.ofHours(1))
                .sign();

    }
}
