package perobobbot.server.plugin.webplugin;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.util.UrlPathHelper;
import perobobbot.lang.ImmutableEntry;
import perobobbot.lang.MapTool;
import perobobbot.plugin.ResourceLocation;
import perobobbot.plugin.ViewInfo;
import perobobbot.plugin.WebPlugin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class WebPluginMappingFactory {

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull ServletContext servletContext;

    private final @NonNull UrlPathHelper urlPathHelper;

    private final @NonNull UUID pluginId;

    private final @NonNull WebPlugin webPlugin;

    public @NonNull ImmutableMap<String, Object> createHandlerMappings() {
        return Stream.concat(
                webPlugin.getResourceLocations()
                         .stream()
                         .flatMap(this::createMapping),
                webPlugin.getViewInformation()
                         .stream()
                         .map(this::createMapping)
        ).collect(MapTool.entryCollector());
    }

    private ImmutableEntry<String, Object> createMapping(@NonNull ViewInfo vi) {
        final var controller = new ParameterizableViewController();
        controller.setApplicationContext(this.applicationContext);
        controller.setServletContext(this.servletContext);
        controller.setViewName(vi.getViewName());
        return ImmutableEntry.of(vi.getUrlPathOrPattern(), controller);
    }

    private Stream<ImmutableEntry<String, Object>> createMapping(ResourceLocation rl) {
        try {
            final var locations = rl.getLocations()
                                    .stream()
                                    .map(PluginProtocolResolver.pluginLocationMapper(pluginId))
                                    .collect(Collectors.toList());

            ResourceHttpRequestHandler handler = new MyResourceHandler();
            handler.setApplicationContext(applicationContext);
            handler.setServletContext(servletContext);
            handler.setUrlPathHelper(urlPathHelper);
            handler.setLocationValues(locations);
            handler.afterPropertiesSet();
            return rl.getPathPatterns().stream().map(p -> ImmutableEntry.of(p, handler));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object formPluginLocation(String s) {
        return "plugin{" + pluginId.toString() + "}:" + s;
    }


    public static class MyResourceHandler extends ResourceHttpRequestHandler {
        @Override
        public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            super.handleRequest(request, response);
        }
    }

}
