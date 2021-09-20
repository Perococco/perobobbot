package perobobbot.extension;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function2;
import perobobbot.plugin.PerobobbotPlugin;

@RequiredArgsConstructor
public class ExtensionPlugin implements Plugin {

    private final @NonNull Function2<? super ModuleLayer, ? super ServiceProvider, ? extends PerobobbotPlugin> pluginFactory;

    @Getter
    private final @NonNull ImmutableSet<Requirement<?>> requirements;

    @Override
    public @NonNull Class<?> getServiceClass() {
        return PerobobbotPlugin.class;
    }

    @Override
    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return pluginFactory.f(pluginLayer, serviceProvider);
    }

}
