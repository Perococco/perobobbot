package perobobbot.discord.client.webclient;

import lombok.NonNull;
import lombok.experimental.Delegate;
import perobobbot.discord.client.api.DiscordServiceWithToken;
import perobobbot.discord.client.api.channel.DiscordChannelServiceWithToken;
import perobobbot.discord.client.webclient.channel.WebClientDiscordChannelService;
import perobobbot.discord.client.webclient.spring.DiscordOAuthWebClientFactory;
import perobobbot.http.WebClientFactory;
import perobobbot.oauth.OAuthWebClientFactory;

public class ProxyDiscordService implements DiscordServiceWithToken {

    public static @NonNull DiscordServiceWithToken lastVersion(@NonNull WebClientFactory webClientFactory) {
        return new ProxyDiscordService(DiscordOAuthWebClientFactory.lastVersion(webClientFactory));
    }

    @Delegate
    private final DiscordChannelServiceWithToken discordChannelService;

    public ProxyDiscordService(@NonNull OAuthWebClientFactory oAuthWebClientFactory) {
        this.discordChannelService = new WebClientDiscordChannelService(oAuthWebClientFactory);
    }
}
