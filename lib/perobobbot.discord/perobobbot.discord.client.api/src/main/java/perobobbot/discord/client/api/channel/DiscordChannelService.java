package perobobbot.discord.client.api.channel;

import lombok.NonNull;
import perobobbot.discord.resources.Message;
import perobobbot.oauth.BotOAuth;
import reactor.core.publisher.Mono;

public interface DiscordChannelService {

    @BotOAuth
    @NonNull Mono<Message> createMessage(@NonNull String channelId, @NonNull CreateMessageParam parameter);

}
