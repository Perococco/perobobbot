package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * All the services the bot provides with a version number for each of them
 */
@RequiredArgsConstructor
public class BotVersionedServices {

    @Getter
    private final @NonNull ImmutableSet<BotVersionedService> services;

}
