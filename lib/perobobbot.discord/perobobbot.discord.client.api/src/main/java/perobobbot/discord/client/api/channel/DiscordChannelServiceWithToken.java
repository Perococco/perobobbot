package perobobbot.discord.client.api.channel;

import lombok.NonNull;
import perobobbot.discord.resources.Message;
import perobobbot.oauth.BotApiToken;
import reactor.core.publisher.Mono;

public interface DiscordChannelServiceWithToken {

    @NonNull Mono<Message> createMessage(@NonNull BotApiToken apiToken, @NonNull String channelId, @NonNull CreateMessageParam parameter);

}
