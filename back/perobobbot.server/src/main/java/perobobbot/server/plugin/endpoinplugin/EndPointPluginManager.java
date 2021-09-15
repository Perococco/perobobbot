package perobobbot.server.plugin.endpoinplugin;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perobobbot.plugin.EndPointPlugin;

public interface EndPointPluginManager {

    @NonNull Subscription addEndPointPlugin(@NonNull EndPointPlugin plugin);
}
