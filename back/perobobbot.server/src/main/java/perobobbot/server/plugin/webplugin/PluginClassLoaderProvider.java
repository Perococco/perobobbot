package perobobbot.server.plugin.webplugin;

import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class PluginClassLoaderProvider {

    private final Map<String,ClassLoader> pluginClassLoaders = new HashMap<>();

    public @NonNull Optional<ClassLoader> findClassLoader(@NonNull String id) {
        return Optional.ofNullable(pluginClassLoaders.get(id));
    }

    @Synchronized
    public void addClassLoader(@NonNull UUID uuid, @NonNull ClassLoader classLoader) {
        this.pluginClassLoaders.put(uuid.toString(),classLoader);
    }

    @Synchronized
    public void removeClassLoader(@NonNull UUID uuid) {
        this.pluginClassLoaders.remove(uuid.toString());
    }
}
