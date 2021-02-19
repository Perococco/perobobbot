package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.UUID;

@NonNull
@RequiredArgsConstructor
public class SimpleBot {

    /**
     * A uniq identifier used to id the bot through the application
     */
    @Getter
    private final @NonNull UUID id;

    @Getter
    private final @NonNull String ownerLogin;

    /**
     * The name of the bot
     */
    @Getter
    private final @NonNull String name;

    @Singular
    @Getter
    private final @NonNull ImmutableMap<Platform,String> credentials;

}
