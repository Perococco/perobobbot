package bot.twitch.chat;

import lombok.NonNull;

public interface ChannelSpecific {

    @NonNull
    Channel channel();
}
