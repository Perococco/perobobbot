package perobobbot.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.lang.ObjectMapperFactory;
import perobobbot.lang.PluginService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@PluginService(type = ObjectMapperFactory.class, apiVersion = ObjectMapperFactory.VERSION, sensitive = false)
public class SpringObjectMapperFactory implements ObjectMapperFactory {

    private final ObjectMapper withoutModules;

    public SpringObjectMapperFactory() {
        this.withoutModules = createWithModules();
    }

    @Override
    public @NonNull ObjectMapper create() {
        return withoutModules;
    }

    @Override
    public @NonNull ObjectMapper createWithExtraModules(@NonNull Module... modules) {
        return createWithModules(modules);
    }

    private static ObjectMapper createWithModules(@NonNull Module... modules) {
        final var mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));


        var moduleByNames = Stream.of(
                Stream.of(new GuavaModule(), new JavaTimeModule(), new Jdk8Module()),
                ServiceLoader.load(JsonModuleProvider.class)
                             .stream()
                             .map(ServiceLoader.Provider::get)
                             .map(JsonModuleProvider::getJsonModules)
                             .flatMap(Collection::stream),
                Arrays.stream(modules)
        ).flatMap(s -> s).collect(Collectors.groupingBy(Module::getModuleName));

        moduleByNames.values()
                     .stream()
                     .filter(Predicate.not(List::isEmpty))
                     .map(l -> l.get(0))
                     .forEach(mapper::registerModule);

        return mapper;
    }


}
