package perobobbot.server.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Instants;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.OAuthSubscriptions;
import perobobbot.oauth.tools.ApiTokenHelperFactory;
import perobobbot.oauth.tools.ServiceWithTokenMapper;
import perobobbot.oauth.tools._private.DefaultServiceWithTokenMapper;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class OAuthConfiguration {

    private final @NonNull ApplicationContext applicationContext;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull Instants instants;

    @Bean
    public @NonNull ServiceWithTokenMapper serviceWithTokenMapper(@NonNull ApiTokenHelperFactory apiTokenHelperFactory) {
        return new DefaultServiceWithTokenMapper(apiTokenHelperFactory);
    }

    @Bean
    public OAuthManager oAuthManager() {
        final var oauthSubscriptions = oAuthSubscriptions();
        final var controllers = applicationContext.getBeansOfType(OAuthController.Factory.class)
                                                  .values()
                                                  .stream()
                                                  .map(f -> f.create(oauthSubscriptions, instants))
                                                  .collect(ImmutableList.toImmutableList());

        final var platformNames = controllers.stream().map(OAuthController::getPlatform).map(Enum::name).collect(Collectors.joining(", "));

        LOG.info("Found OAuthController for : {}", platformNames);
        return OAuthManager.create(controllers);
    }


    @Bean(destroyMethod = "removeAll")
    public OAuthSubscriptions oAuthSubscriptions() {
        return OAuthSubscriptions.create(instants, webHookManager);
    }


}
