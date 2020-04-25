package perococco.bot.twitch.chat.state;

import bot.twitch.chat.message.from.MessageFromTwitchAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateUpdater extends MessageFromTwitchAdapter {

    @NonNull
    private final ConnectionIdentity identity;
}
