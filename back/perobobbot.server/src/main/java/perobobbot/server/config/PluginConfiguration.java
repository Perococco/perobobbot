package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.plugin.PluginService;
import perobobbot.server.config.extension.ServiceProviderFactory;
import perobobbot.server.config.extension.SimpleServiceProviderFactory;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class PluginConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public @NonNull ServiceProviderFactory serviceProviderFactory() {
        final var pluginServices = applicationContext.getBeansWithAnnotation(PluginService.class).values();
        return new SimpleServiceProviderFactory(new ArrayList<>(pluginServices));
    }

}
