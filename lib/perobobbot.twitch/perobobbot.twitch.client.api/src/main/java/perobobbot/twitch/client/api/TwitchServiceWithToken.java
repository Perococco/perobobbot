package perobobbot.twitch.client.api;

import perobobbot.twitch.client.api.channel.TwitchServiceChannelWithToken;
import perobobbot.twitch.client.api.channelpoints.TwitchServiceChannelPointsWithToken;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSubWithToken;
import perobobbot.twitch.client.api.games.TwitchServiceGamesWithToken;

public interface TwitchServiceWithToken extends
        TwitchServiceEventSubWithToken,
        TwitchServiceChannelWithToken,
        TwitchServiceGamesWithToken,
        TwitchServiceChannelPointsWithToken {


}
