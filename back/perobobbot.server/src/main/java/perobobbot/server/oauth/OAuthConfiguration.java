package perobobbot.server.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.oauth.ClientProperties;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;

import java.nio.file.Path;

@Configuration
@RequiredArgsConstructor
public class OAuthConfiguration {

    @Value("${app.config.dir}/server/client_properties.json")
    private final Path clientPropertyFile;

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public OAuthManager oAuthManager() {
        final var controllers = ImmutableList.copyOf(applicationContext.getBeansOfType(OAuthController.class)
                                                                       .values());
        return OAuthManager.create(controllers);
    }

    @Bean
    public @NonNull ClientProperties clientCredential() {
        final var clientProperties = ClientProperties.fromFile(clientPropertyFile);
        System.out.println(clientProperties);
        return clientProperties;

//        final var clientCredential =  new ClientProperties(clientId, Secret.with(clientSecret), Todo.TODO());
//        return clientCredential;
    }
}
