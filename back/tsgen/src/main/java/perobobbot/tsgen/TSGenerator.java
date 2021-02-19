package perobobbot.tsgen;

import com.blueveery.springrest2ts.Rest2tsGenerator;
import com.blueveery.springrest2ts.converters.*;
import com.blueveery.springrest2ts.filters.AndFilterOperator;
import com.blueveery.springrest2ts.filters.HasAnnotationJavaTypeFilter;
import com.blueveery.springrest2ts.filters.NotJavaTypeFilter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.TypeScript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class TSGenerator {

    public static void main(String[] args) throws IOException {
        new TSGenerator().generate();
    }

    final Rest2tsGenerator generator = new Rest2tsGenerator();

    private void generate() throws IOException {
        this.setupClassFiltering();
        generator.setEnumConverter(new JavaEnumToTsEnumConverter(false));
        generator.getCustomTypeMapping().put(UUID.class, TypeMapper.tsString);
        generator.getCustomTypeMapping().put(Locale.class, TypeMapper.tsString);

        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        jacksonObjectMapper.setFieldsVisibility(JsonAutoDetect.Visibility.ANY);

        var modelClassesConverter = new ModelClassesToTsInterfacesConverter(jacksonObjectMapper);
        generator.setModelClassesConverter(modelClassesConverter);

        // Spring REST controllers converter
        var restClassesConverter = new SpringRestToTsConverter(new AxiosImplementationGenerator());
        generator.setRestClassesConverter(restClassesConverter);

        // set of java root packages for class scanning
        var javaPackageSet = Set.of(
                "perobobbot.lang",
                "perobobbot.data.com",
                "perobobbot.rest.com",
                "perobobbot.security.com",
                "perobobbot.rest.controller");
        var outputDirectory = findOutputPath();
        generator.generate(javaPackageSet, outputDirectory);
    }

    private Path findOutputPath() {
        final UnaryOperator<Path> createTarget = p -> Stream.of("front", "svelte", "src", "server").sequential().reduce(
                p,
                Path::resolve,
                Path::resolve);

        return Stream.iterate(Path.of(".").toAbsolutePath(), p -> !Files.isDirectory(p.resolve("bot")), Path::getParent)
                     .peek(p -> System.out.println(p))
                     .map(createTarget)
                     .filter(Files::isDirectory)
                     .findFirst().orElseThrow(() -> new IllegalStateException("Could not find output directory"));

    }

    private void setupClassFiltering() {
        generator.setModelClassesCondition(new AndFilterOperator(List.of(
                new HasAnnotationJavaTypeFilter(TypeScript.class),
                new NotJavaTypeFilter(new HasAnnotationJavaTypeFilter(NoTypeScript.class))
        )));
        generator.setRestClassesCondition(new AndFilterOperator(List.of(
                new HasAnnotationJavaTypeFilter(RestController.class),
                new NotJavaTypeFilter(new HasAnnotationJavaTypeFilter(NoTypeScript.class))
        )));
    }

}
