package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotVersionedServices {

    @Getter
    private final @NonNull ImmutableSet<BotVersionedService> services;

}
