package perobobbot.server.plugin.webplugin;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.*;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PluginProtocolResolver implements ProtocolResolver, ResourceLoaderAware {

    public static @NonNull UnaryOperator<String> pluginLocationMapper(@NonNull UUID id) {
        return s -> "plugin{"+id.toString()+"}:"+s;
    }

    private static final Pattern PREFIX_PATTERN = Pattern.compile("plugin\\{([-0-9a-zA-Z]+)}:(.+)");

    private final @NonNull PluginClassLoaderProvider pluginClassLoaderProvider;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        if (resourceLoader instanceof DefaultResourceLoader) {
            ((DefaultResourceLoader) resourceLoader).addProtocolResolver(this);
        }
    }

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        final var matcher = PREFIX_PATTERN.matcher(location);
        if (!matcher.matches()) {
            return null;
        }
        final var id = matcher.group(1);
        final var subLocation = matcher.group(2);
        final var resource = resourceLoader.getResource(subLocation);

        if (!(resource instanceof ClassPathResource)) {
            return resource;
        }
        final var classPathResource = (ClassPathResource)resource;
        final var path = classPathResource.getPath();

        return pluginClassLoaderProvider.findClassLoader(id)
                                        .map(c -> new ClassPathResource(path,c))
                                        .orElse(classPathResource);

    }
}
