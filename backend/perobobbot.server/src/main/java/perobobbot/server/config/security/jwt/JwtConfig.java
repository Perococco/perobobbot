package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.jpa.repository.UserRepository;

import java.util.Base64;
/**
 * @author Perococco
 */
@Configuration
public class JwtConfig {

    private static final String KEY = Base64.getEncoder().encodeToString("totoaotireirieaoeraer".getBytes());

    private static final String ISSUER = "PEROBOBBOT";

    private final UserRepository userRepository;

//    @Value("${application.jwt.key:ABSrusdfkalAKSDFHJA}")
    private final String key;

    public JwtConfig(@NonNull UserRepository userRepository, @Value("${application.jwt.key:ABSrusdfkalAKSDFHJA}") String key) {
        this.userRepository = userRepository;
        this.key = key;
    }

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager(key,ISSUER,userRepository);
    }
}
