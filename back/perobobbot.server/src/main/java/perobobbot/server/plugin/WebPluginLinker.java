package perobobbot.server.plugin;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import perobobbot.plugin.PluginList;
import perobobbot.plugin.WebPlugin;

@Component
@RequiredArgsConstructor
public class WebPluginLinker implements WebMvcConfigurer {

    private final @NonNull PluginList pluginList;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        pluginList.streamPlugins(WebPlugin.class)
                  .forEach(w -> w.registerView(
                          (urlPathOrPattern, viewName) -> registry.addViewController(urlPathOrPattern).setViewName(viewName))
                  );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        pluginList.streamPlugins(WebPlugin.class)
                  .forEach(w -> w.registerResources(
                          (pathPatterns, resourceLocations) -> registry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations)));
    }
}
