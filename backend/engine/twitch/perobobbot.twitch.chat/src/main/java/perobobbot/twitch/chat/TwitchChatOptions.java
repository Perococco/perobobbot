package perobobbot.twitch.chat;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.common.lang.Secret;

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
