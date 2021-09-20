package perobobbot.server.plugin.endpoinplugin;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perobobbot.plugin.EndPointPluginData;

public interface EndPointPluginManager {

    @NonNull Subscription addEndPointPlugin(@NonNull EndPointPluginData plugin);
}
