package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.jpa.repository.UserRepository;
/**
 * @author Perococco
 */
@Configuration
public class JwtConfig {

    private static final String ISSUER = "PEROBOBBOT";

    private final UserRepository userRepository;

    private final String key;

    public JwtConfig(@NonNull UserRepository userRepository, @Value("${perobot.jwtconfig.key:shouldBeSetThroughtProperty}") String key) {
        this.userRepository = userRepository;
        this.key = key;
    }

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager(key,ISSUER,userRepository);
    }
}
