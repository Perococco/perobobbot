package perobobbot.server.plugin;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bom {

    @Getter
    private final @NonNull ImmutableList<Dependency> dependencies;
}
