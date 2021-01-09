package perobobbot.tsgen;

import com.blueveery.springrest2ts.Rest2tsGenerator;
import com.blueveery.springrest2ts.converters.*;
import com.blueveery.springrest2ts.filters.AndFilterOperator;
import com.blueveery.springrest2ts.filters.HasAnnotationJavaTypeFilter;
import com.blueveery.springrest2ts.filters.NotJavaTypeFilter;
import com.blueveery.springrest2ts.implgens.Angular4ImplementationGenerator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.TypeScript;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TSGenerator {

    public static void main(String[] args) throws IOException {
        new TSGenerator().generate();
    }

    final Rest2tsGenerator generator = new Rest2tsGenerator();

    private void generate() throws IOException {
        this.setupClassFiltering();
        generator.setEnumConverter(new JavaEnumToTsEnumConverter());
        generator.getCustomTypeMapping().put(UUID.class, TypeMapper.tsString);

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
                "perobobbot.rest.controller");
        generator.generate(javaPackageSet, Paths.get("./extensions/perobobbot.dashboard-svelte/src/ts-code"));
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
