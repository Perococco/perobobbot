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

@Configuration
@RequiredArgsConstructor
@Log4j2
public class OAuthConfiguration {

    private final @NonNull ApplicationContext applicationContext;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull Instants instants;

    @Bean(destroyMethod = "dispose")
    public OAuthManager oAuthManager() {
        final var oauthSubscriptions = oAuthSubscriptions();
        final var controllers = applicationContext.getBeansOfType(OAuthController.Factory.class)
                                                  .values()
                                                  .stream()
                                                  .map(f -> f.create(oauthSubscriptions, instants))
                                                  .collect(ImmutableList.toImmutableList());
        return OAuthManager.create(controllers);
    }


    @Bean(destroyMethod = "removeAll")
    public OAuthSubscriptions oAuthSubscriptions() {
        return new OAuthSubscriptions(instants, webHookManager);
    }


}
