package perobobbot.twitch.chat.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Instants;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.twitch.chat.TwitchChatPlatform;

@Configuration

public class TwitchConfiguration {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.PRIMARY,"Twitch Chat Platform", TwitchConfiguration.class);
    }

    @Bean
    public TwitchChatPlatform twitchChatPlatform(@NonNull Instants instants) {
        return new TwitchChatPlatform(instants);
    }
}
