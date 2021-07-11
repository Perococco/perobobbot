package perobobbot.twitch.client.api;

import perobobbot.twitch.client.api.channel.TwitchServiceChannel;
import perobobbot.twitch.client.api.channelpoints.TwitchServiceChannelPoints;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSub;
import perobobbot.twitch.client.api.games.TwitchServiceGames;

public interface TwitchService extends
        TwitchServiceChannel,
        TwitchServiceEventSub,
        TwitchServiceGames,
        TwitchServiceChannelPoints {
    int VERSION = 1;
}
