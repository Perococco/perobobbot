package perobobbot.server.plugin.webplugin;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;
import perobobbot.plugin.WebPlugin;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebPluginHandlerMapping extends AbstractHandlerMapping implements WebPluginManager {


    private final @NonNull PluginClassLoaderProvider pluginClassLoaderProvider;

    private ImmutableMap<UUID, HandlerMapping> pluginMappings = ImmutableMap.of();

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        Object handler = null;
        for (HandlerMapping value : pluginMappings.values()) {
            handler = value.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    @Override
    public @NonNull Subscription addWebPlugin(@NonNull WebPlugin webPlugin) {
        final UUID pluginId = UUID.randomUUID();
        pluginClassLoaderProvider.addClassLoader(pluginId,webPlugin.resourceClassLoader());
        final var mapper = createMapper(pluginId, webPlugin);
        final var urlMap = mapper.createHandlerMappings();
        final var mapping = PluginMapping.create(urlMap);

        return Subscription.multi(
                addMapping(mapping),
                () -> pluginClassLoaderProvider.removeClassLoader(pluginId)
        );
    }

    private @NonNull WebPluginMappingFactory createMapper(@NonNull UUID pluginId, @NonNull WebPlugin webPlugin) {
        return new WebPluginMappingFactory(getApplicationContext(),
                                           getServletContext(),
                                           getUrlPathHelper(),
                                           pluginId,
                                           webPlugin);
    }

    @Synchronized
    private @NonNull Subscription addMapping(@NonNull HandlerMapping handlerMapping) {
        var id = UUID.randomUUID();
        this.pluginMappings = MapTool.add(this.pluginMappings, id, handlerMapping);
        return () -> removeWebPlugin(id);
    }

    @Synchronized
    private void removeWebPlugin(@NonNull UUID uuid) {
        this.pluginMappings = MapTool.remove(pluginMappings,uuid);
    }

    private static class PluginMapping extends AbstractUrlHandlerMapping {

        public static PluginMapping create(@NonNull ImmutableMap<String,Object> handlers) {
            final var result = new PluginMapping();
            handlers.forEach(result::registerHandler);
            return result;
        }

    }
}
