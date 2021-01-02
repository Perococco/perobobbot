package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.service.UserProvider;

/**
 * @author Perococco
 */
@Configuration
public class JwtConfig {

    private static final String ISSUER = "PEROBOBBOT";

    private final String key;

    public JwtConfig(@Value("${perobot.jwtconfig.key:shouldBeSetThroughtProperty}") String key) {
        this.key = key;
    }

    @Bean
    public JWTokenManager jwtTokenManager(@NonNull UserProvider userProvider) {
        return new JWTokenManager(key, ISSUER, userProvider);
    }
}
