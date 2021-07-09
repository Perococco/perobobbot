package perobobbot.twitch.client.api;

import perobobbot.twitch.client.api.channelpoints.TwitchServiceChannelPoints;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSub;
import perobobbot.twitch.client.api.games.TwitchServiceGames;

public interface TwitchService extends
        TwitchServiceEventSub,
        TwitchServiceGames,
        TwitchServiceChannelPoints {
    int VERSION = 1;
}
