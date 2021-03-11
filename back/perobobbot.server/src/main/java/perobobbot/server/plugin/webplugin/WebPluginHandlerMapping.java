package perobobbot.server.plugin.webplugin;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;
import perobobbot.plugin.WebPlugin;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebPluginHandlerMapping implements HandlerMapping, WebPluginManager {

    private final @NonNull WebPluginMappingFactory webPluginMappingFactory;

    private ImmutableMap<UUID, HandlerMapping> pluginMappings = ImmutableMap.of();

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        for (HandlerMapping value : pluginMappings.values()) {
            var result = value.getHandler(request);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Override
    public @NonNull Subscription addWebPlugin(@NonNull WebPlugin webPlugin) {
        return webPluginMappingFactory
                .createHandlerMappings(webPlugin)
                .stream().map(this::addWebPlugin)
                .collect(Subscription.COLLECTOR);
    }

    @Synchronized
    private @NonNull Subscription addWebPlugin(@NonNull HandlerMapping handlerMapping) {
        var id = UUID.randomUUID();
        this.pluginMappings = MapTool.add(this.pluginMappings, id, handlerMapping);
        return () -> removeWebPlugin(id);
    }

    @Synchronized
    private void removeWebPlugin(@NonNull UUID uuid) {
        this.pluginMappings = MapTool.remove(pluginMappings,uuid);
    }
}
