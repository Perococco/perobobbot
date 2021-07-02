package perobobbot.tsgen;

import com.blueveery.springrest2ts.Rest2tsGenerator;
import com.blueveery.springrest2ts.converters.JacksonObjectMapper;
import com.blueveery.springrest2ts.converters.ModelClassesToTsInterfacesConverter;
import com.blueveery.springrest2ts.converters.SpringRestToTsConverter;
import com.blueveery.springrest2ts.converters.TypeMapper;
import com.blueveery.springrest2ts.filters.AndFilterOperator;
import com.blueveery.springrest2ts.filters.HasAnnotationJavaTypeFilter;
import com.blueveery.springrest2ts.filters.NotJavaTypeFilter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.TypeScript;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class TSGenerator {

    public static String camelToSnake(String str) {
        String searchPattern = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(searchPattern, replacement).toLowerCase();
    }

    public static void main(String[] args) throws IOException {
        new TSGenerator().generate();
    }

    final Rest2tsGenerator generator = new Rest2tsGenerator();

    private void generate() throws IOException {
        this.setupClassFiltering();
        generator.setEnumConverter(new PerobobbotEnumConverter(false));
        generator.getCustomTypeMapping().put(URI.class, TypeMapper.tsString);
        generator.getCustomTypeMapping().put(UUID.class, TypeMapper.tsString);
        generator.getCustomTypeMapping().put(Instant.class, TypeMapper.tsString);
        generator.getCustomTypeMapping().put(Locale.class, TypeMapper.tsString);

        JacksonObjectMapper jacksonObjectMapper = new MyMapper();
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
                "perobobbot.twitch.oauth.api",
                "perobobbot.rest.controller");
        var outputDirectory = findOutputPath();
        generator.generate(javaPackageSet, outputDirectory);
    }

    private Path findOutputPath() {
        final Path targetPath = Stream.of("_generated","front","api").sequential().reduce(
                Path.of("."),
                Path::resolve,
                Path::resolve).normalize();

        if (!Files.isDirectory(targetPath)) {
            throw new IllegalStateException("Could not find output directory '"+targetPath.toAbsolutePath()+"'");
        }

        return targetPath;
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

    private static class MyMapper extends JacksonObjectMapper {
        @Override
        public String getPropertyName(Field field) {
            return camelToSnake(super.getPropertyName(field));
        }

        @Override
        public String getPropertyName(Method method, boolean isGetter) {
            return camelToSnake(super.getPropertyName(method, isGetter));
        }
    }


}
