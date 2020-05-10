package bot.twitch.chat;

import bot.common.lang.Secret;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class TwitchChatOptions {

    @NonNull
    private final String nick;

    @NonNull
    private final Secret secret;

    @NonNull
    @Singular
    private final ImmutableSet<Channel> channels;
}
