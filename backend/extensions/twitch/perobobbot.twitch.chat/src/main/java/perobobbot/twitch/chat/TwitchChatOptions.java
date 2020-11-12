package perobobbot.twitch.chat;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
@Builder
public class TwitchChatOptions {

    @NonNull String nick;

    @NonNull Secret secret;

    @NonNull
    @Singular
    ImmutableSet<Channel> channels;
}
