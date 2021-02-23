package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.plugin.ExtensionInfo;
import perobobbot.lang.MapTool;
import perobobbot.lang.ThrowableTool;
import perobobbot.plugin.ExtensionPlugin;
import perobobbot.plugin.PluginList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class ExtensionLister {

    public static @NonNull ImmutableMap<String, ExtensionInfo> gatherAllExtensions(
            @NonNull PluginList pluginList,
            @NonNull ExtensionPlugin.Parameters parameters) {

        return new ExtensionLister(pluginList,parameters).gatherAllExtensions();
    }

    private final @NonNull PluginList pluginList;

    private final @NonNull ExtensionPlugin.Parameters parameters;

    private @NonNull ImmutableMap<String, ExtensionInfo> gatherAllExtensions() {
        final var factoriesByName = pluginList.streamPlugins(ExtensionPlugin.class)
                                              .collect(Collectors.groupingBy(ExtensionPlugin::getExtensionName));

        return factoriesByName.entrySet()
                              .stream()
                              .map(this::selectOneExtension)
                              .flatMap(Optional::stream)
                              .map(this::createExtensionInfo)
                              .flatMap(Optional::stream)
                              .collect(MapTool.collector(ExtensionInfo::getExtensionName));
    }

    private @NonNull Optional<ExtensionInfo> createExtensionInfo(@NonNull ExtensionPlugin plugin) {
        try {
            return Optional.of(plugin.create(parameters));
        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            LOG.warn("Could not create extension plugin : {} : {}",plugin.getExtensionName(), e.getMessage());
            LOG.debug(e);
            return Optional.empty();
        }
    }

    private @NonNull Optional<ExtensionPlugin> selectOneExtension(@NonNull Map.Entry<String, List<ExtensionPlugin>> entry) {
        final var name = entry.getKey();
        final var list = entry.getValue();
        if (list.isEmpty()) {
            LOG.error("No extension with the name '{}' : this is a bug", name);
            return Optional.empty();
        }
        if (list.size() > 1) {
            LOG.warn("Duplicate extension with name '{}' : {}", name, list);
        }
        return Optional.ofNullable(list.get(0));
    }

}
