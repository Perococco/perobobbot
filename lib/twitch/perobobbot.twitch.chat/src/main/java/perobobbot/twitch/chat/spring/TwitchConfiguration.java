package perobobbot.twitch.chat.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Packages;
import perobobbot.twitch.chat.TwitchChatPlatform;

@Configuration
public class TwitchConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch Chat Platform",TwitchConfiguration.class);
    }

    @Bean
    public TwitchChatPlatform twitchChatPlatform() {
        return new TwitchChatPlatform();
    }
}
