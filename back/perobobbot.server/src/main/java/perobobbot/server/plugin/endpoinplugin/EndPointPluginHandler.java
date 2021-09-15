package perobobbot.server.plugin.endpoinplugin;

import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import perobobbot.lang.Subscription;
import perobobbot.lang.ThrowableTool;
import perobobbot.plugin.EndPointPlugin;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Component
public class EndPointPluginHandler extends AbstractHandlerMapping implements EndPointPluginManager {


    private final @NonNull Map<UUID, HandlerMapping> pluginMappings = new LinkedHashMap<>();


    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) {
        return pluginMappings.values()
                             .stream()
                             .map(h -> tryGetHandler(h, request))
                             .filter(Objects::nonNull)
                             .findFirst()
                             .orElse(null);
    }


    private Object tryGetHandler(@NonNull HandlerMapping handlerMapping, @NonNull HttpServletRequest request) {
        try {
            return handlerMapping.getHandler(request);
        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return null;
        }
    }

    @Override
    @Synchronized
    public @NonNull Subscription addEndPointPlugin(@NonNull EndPointPlugin plugin) {
        final var uuid = findUnusedUUID();
        pluginMappings.put(uuid, plugin.getHandlerMapping());
        return () -> removeEndPointPlugin(uuid);
    }

    private @NonNull UUID findUnusedUUID() {
        while (true) {
            final var uuid = UUID.randomUUID();
            if (!pluginMappings.containsKey(uuid)) {
                return uuid;
            }
        }
    }

    @Synchronized
    private void removeEndPointPlugin(@NonNull UUID uuid) {
        pluginMappings.remove(uuid);
    }

}
