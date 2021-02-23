package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class PluginList {

    private final @NonNull ImmutableList<Plugin> plugins;

    public @NonNull Stream<FunctionalPlugin> streamFunctionalPlugins() {
        return streamPlugins(FunctionalPlugin.class);
    }

    public @NonNull <P extends Plugin> Stream<P> streamPlugins(@NonNull Class<P> pluginType) {
        return plugins.stream()
                      .filter(pluginType::isInstance)
                      .map(pluginType::cast);
    }

    public @NonNull Stream<Plugin> streamAllPlugins() {
        return plugins.stream();
    }
}
