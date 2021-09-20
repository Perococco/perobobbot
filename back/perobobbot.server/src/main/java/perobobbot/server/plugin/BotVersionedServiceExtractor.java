package perobobbot.server.plugin;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import perobobbot.lang.PluginService;
import perobobbot.lang.PluginServices;
import perobobbot.lang.fp.Function1;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Retrieve all plugin service from the application context for a bean identified by its name
 */
@RequiredArgsConstructor
public class BotVersionedServiceExtractor {

    public static @NonNull Stream<BotVersionedService> extract(@NonNull ApplicationContext applicationContext, @NonNull String beanName) {
        return new BotVersionedServiceExtractor(applicationContext, beanName).extract();
    }

    public static @NonNull Function1<String,Stream<BotVersionedService>> with(@NonNull ApplicationContext applicationContext) {
        return name -> extract(applicationContext,name);
    }


    private final @NonNull ApplicationContext applicationContext;
    private final @NonNull String beanName;

    private Stream<BotVersionedService> extract() {
        return getPluginServices().map(this::convertToBotService);

    }

    private @NonNull Stream<PluginService> getPluginServices() {
        return Stream.concat(
                pluginServiceFromSingleAnnotation(),
                pluginServiceFromMultipleAnnotation()
        ).filter(Objects::nonNull);
    }

    private @NonNull Stream<PluginService> pluginServiceFromSingleAnnotation() {
        return Stream.of(applicationContext.findAnnotationOnBean(beanName, PluginService.class));
    }

    private @NonNull Stream<PluginService> pluginServiceFromMultipleAnnotation() {
        return Stream.of(applicationContext.findAnnotationOnBean(beanName, PluginServices.class))
                     .filter(Objects::nonNull)
                     .map(PluginServices::value)
                     .flatMap(Stream::of);
    }


    private @NonNull BotVersionedService convertToBotService(@NonNull PluginService pluginService) {
        final Object bean = applicationContext.getBean(beanName);
        final Class<?> annotationType = pluginService.type();
        final Class<?> type;
        if (Void.class.equals(annotationType) || !annotationType.isInstance(bean)) {
            type = bean.getClass();
        } else {
            type = annotationType;
        }
        return new BotVersionedService(type, bean, pluginService.apiVersion(), pluginService.sensitive());
    }


}
