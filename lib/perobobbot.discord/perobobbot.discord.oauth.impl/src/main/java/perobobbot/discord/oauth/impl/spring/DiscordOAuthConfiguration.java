package perobobbot.discord.oauth.impl.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.discord.oauth.impl.DiscordOAuthController;
import perobobbot.lang.Packages;
import perobobbot.oauth.OAuthController;

@Configuration
public class DiscordOAuthConfiguration {


    public static @NonNull Packages provider() {
        return Packages.with("Discord OAuth", DiscordOAuthConfiguration.class);
    }

    @Bean
    public @NonNull OAuthController.Factory discordOAuthController() {
        return (oAuthSubscriptions, instants) -> new DiscordOAuthController(oAuthSubscriptions, WebClient.create(), instants);
    }

}
