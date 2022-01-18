package perobobbot.discord.client.webclient.channel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.client.api.channel.CreateMessageParam;
import perobobbot.discord.client.api.channel.DiscordChannelServiceWithToken;
import perobobbot.discord.resources.Message;
import perobobbot.oauth.BotApiToken;
import perobobbot.oauth.OAuthWebClientFactory;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientDiscordChannelService implements DiscordChannelServiceWithToken {

    private final @NonNull OAuthWebClientFactory webClientFactory;

    @Override
    public @NonNull Mono<Message> createMessage(@NonNull BotApiToken apiToken, @NonNull String channelId, @NonNull CreateMessageParam parameter) {
        return webClientFactory.create(apiToken)
                               .post("/channels/%s/messages".formatted(channelId))
                               .body(Mono.just(parameter), CreateMessageParam.class)
                               .retrieve()
                               .bodyToMono(Message.class);
    }
}
