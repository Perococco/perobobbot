package perobobbot.server.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class OAuthConfiguration {

    private final @NonNull ApplicationContext applicationContext;
    private final @EventService
    @NonNull ClientService clientService;

    @Bean
    public OAuthManager oAuthManager() {
        final var controllers = applicationContext.getBeansOfType(OAuthController.class)
                                                  .values()
                                                  .stream()
                                                  .collect(ImmutableList.toImmutableList());
        return OAuthManager.create(controllers);
    }

}
