package perobobbot.server.plugin.webplugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import perobobbot.plugin.WebPlugin;

import javax.servlet.ServletContext;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class WebPluginMappingFactory {

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull ServletContext servletContext;

    public @NonNull ImmutableList<HandlerMapping> createHandlerMappings(@NonNull WebPlugin webPlugin) {
        final var vcRegistry = new MyViewControllerRegistry(applicationContext);
        final var rhRegistry = new MyResourceHandlerRegistry(applicationContext, servletContext);

        webPlugin.getViewInformation().forEach(
                vi -> vcRegistry.addViewController(vi.getUrlPathOrPattern()).setViewName(vi.getViewName()));
        webPlugin.getResourceLocations().forEach(
                rl -> rhRegistry.addResourceHandler(rl.getPathPattern()).addResourceLocations(rl.getLocations()));


        return Stream.of(vcRegistry.buildHandlerMapping(), rhRegistry.getHandlerMapping())
                     .filter(Objects::nonNull)
                     .collect(ImmutableList.toImmutableList());
    }

    private static class MyViewControllerRegistry extends ViewControllerRegistry {

        /**
         * Class constructor with {@link ApplicationContext}.
         *
         * @param applicationContext
         * @since 4.3.12
         */
        public MyViewControllerRegistry(ApplicationContext applicationContext) {
            super(applicationContext);
        }

        @Override
        public SimpleUrlHandlerMapping buildHandlerMapping() {
            return super.buildHandlerMapping();
        }
    }


    private static class MyResourceHandlerRegistry extends ResourceHandlerRegistry {

        public MyResourceHandlerRegistry(ApplicationContext applicationContext, ServletContext servletContext) {
            super(applicationContext, servletContext);
        }

        @Override
        public AbstractHandlerMapping getHandlerMapping() {
            return super.getHandlerMapping();
        }
    }
}
