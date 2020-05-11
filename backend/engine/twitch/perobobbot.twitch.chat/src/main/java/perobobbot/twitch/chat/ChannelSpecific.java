package perobobbot.twitch.chat;

import lombok.NonNull;

public interface ChannelSpecific {

    @NonNull
    Channel getChannel();
}
