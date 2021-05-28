package perobobbot.lang;

import lombok.*;

import java.util.UUID;

@TypeScript
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bot {

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

}
