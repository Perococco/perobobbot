package perobobbot.data.jpa.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.jpa.repository.tools.PlatformUserHelper;
import perobobbot.data.jpa.repository.tools.UserIdentityRetriever;
import perobobbot.data.jpa.repository.tools.UserTokenSaver;
import perobobbot.data.jpa.repository.tools.DefaultUserTokenSaver;
import perobobbot.lang.TextEncryptor;
import perobobbot.oauth.OAuthManager;

@Configuration
@RequiredArgsConstructor
public class RepositoryToolConfiguration {

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull ClientRepository clientRepository;
    private final @NonNull UserRepository userRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull TextEncryptor textEncryptor;
    private final @NonNull DiscordUserRepository discordUserRepository;
    private final @NonNull TwitchUserRepository twitchUserRepository;

    @Bean
    public UserIdentityRetriever userIdentityRetriever() {
        return new UserIdentityRetriever(oAuthManager, clientRepository, textEncryptor);
    }


    @Bean
    public PlatformUserHelper platformIdentityHelperVisitor() {
        return new PlatformUserHelper(discordUserRepository, twitchUserRepository);
    }

    @Bean
    public UserTokenSaver userTokenSaver(@NonNull UserIdentityRetriever userIdentityRetriever, @NonNull PlatformUserHelper platformUserHelper) {
        return new DefaultUserTokenSaver(
                userRepository,
                userTokenRepository,
                userIdentityRetriever,
                textEncryptor,
                platformUserHelper
        );
    }
}
