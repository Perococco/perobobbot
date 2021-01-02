package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import perobobbot.data.service.UserProvider;
import perobobbot.data.service.UserService;

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
    public JWTokenServiceFromUserService jwtTokenManager(@NonNull UserProvider userProvider) {
        return new JWTokenServiceFromUserService(key, ISSUER, userProvider);
    }
}
