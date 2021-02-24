package perobobbot.server.config.extension;

import lombok.NonNull;
import perobobbot.plugin.PluginUsingServices;
import perobobbot.plugin.ServiceProvider;

import java.util.Optional;

public interface ServiceProviderFactory {

    void addService(@NonNull Object service);

    @NonNull Optional<ServiceProvider> createServiceProvider(@NonNull PluginUsingServices pluginUsingServices);

}
