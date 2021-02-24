package perobobbot.server.config;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.plugin.PluginService;
import perobobbot.server.config.extension.ServiceProviderFactory;
import perobobbot.server.config.extension.SimpleServiceProviderFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PluginConfiguration {

    @Bean
    public @NonNull ServiceProviderFactory serviceProviderFactory(List<PluginService> pluginServices) {
        return new SimpleServiceProviderFactory(new ArrayList<>(pluginServices));
    }

}
