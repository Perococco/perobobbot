package perobobbot.server.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;

@Configuration
@RequiredArgsConstructor
public class OAuthConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public OAuthManager oAuthManager() {
        final var controllers = ImmutableList.copyOf(applicationContext.getBeansOfType(OAuthController.class)
                                                                       .values());
        return OAuthManager.create(controllers);

    }
}
