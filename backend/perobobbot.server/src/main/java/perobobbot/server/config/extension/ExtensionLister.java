package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionInfo;
import perobobbot.lang.MapTool;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class ExtensionLister {

    public static @NonNull ImmutableMap<String, ExtensionInfo> gatherAllExtensions(@NonNull ApplicationContext applicationContext) {
        return new ExtensionLister(applicationContext).gatherAllExtensions();
    }

    private final @NonNull ApplicationContext applicationContext;

    private @NonNull ImmutableMap<String, ExtensionInfo> gatherAllExtensions() {
        final var factoriesByName = applicationContext.getBeansOfType(ExtensionFactory.class)
                                                      .values()
                                                      .stream()
                                                      .collect(Collectors.groupingBy(ExtensionFactory::getExtensionName));

        return factoriesByName.entrySet()
                              .stream()
                              .map(this::selectOneExtension)
                              .flatMap(Optional::stream)
                              .map(ExtensionFactory::create)
                              .collect(MapTool.collector(ExtensionInfo::getExtensionName));
    }

    private @NonNull Optional<ExtensionFactory> selectOneExtension(@NonNull Map.Entry<String, List<ExtensionFactory>> entry) {
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
