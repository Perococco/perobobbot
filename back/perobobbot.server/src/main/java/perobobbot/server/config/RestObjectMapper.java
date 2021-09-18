package perobobbot.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.lang.PluginService;
import perobobbot.lang.PluginServices;

import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@PluginService(type = ObjectMapper.class, apiVersion = RestObjectMapper.VERSION,sensitive = false)
public class RestObjectMapper extends ObjectMapper {

    public static final int VERSION =1 ;

    public static RestObjectMapper create() {
        final var mapper = new RestObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));



        var moduleByNames = Stream.concat(
                Stream.of(new GuavaModule(), new JavaTimeModule(), new Jdk8Module()),
                ServiceLoader.load(JsonModuleProvider.class)
                             .stream()
                             .map(ServiceLoader.Provider::get)
                             .map(JsonModuleProvider::getJsonModules)
                             .flatMap(Collection::stream)
        ).collect(Collectors.groupingBy(Module::getModuleName));

        moduleByNames.values()
                     .stream()
                     .filter(Predicate.not(List::isEmpty))
                     .map(l -> l.get(0))
                     .forEach(mapper::registerModule);

        return mapper;
    }

    @Override
    public ObjectMapper copy() {
        return create();
    }
}
