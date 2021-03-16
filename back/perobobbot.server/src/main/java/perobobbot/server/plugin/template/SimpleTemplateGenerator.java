package perobobbot.server.plugin.template;

import com.google.common.collect.ImmutableList;
import jplugman.api.Version;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import perobobbot.lang.TemplateGenerator;
import perobobbot.server.plugin.Bom;
import perobobbot.server.plugin.BotVersionedServiceProvider;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Builder
public class SimpleTemplateGenerator implements TemplateGenerator {


    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    public static void init() {
        if (INITIALIZED.compareAndSet(false, true)) {
            Properties p = new Properties();
            p.setProperty("resource.loaders", "class");
            p.setProperty("resource.loader.class.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            Velocity.init(p);
        }
    }

    private final @NonNull Bom bom;
    private final @NonNull Version applicationVersion;
    private final @NonNull BotVersionedServiceProvider serviceProvider;

    @Override
    public @NonNull Path generate(@NonNull String groupId, @NonNull String artifactId) throws IOException {
        return new Executor(groupId,artifactId).generate();
    }

    @RequiredArgsConstructor
    private class Executor {

        private final @NonNull String groupId;
        private final @NonNull String artifactId;

        private Path outputPath;
        private ImmutableList<String> structure;

        private VelocityContext context;

        public @NonNull Path generate() throws IOException {
            init();
            createContext();
            createOutputPath();
            readStructureFile();

            structure.forEach(this::performCopy);

            return outputPath;
        }

        private void performCopy(String fileTemplate) {
            try {
                final var result = prepareTarget(fileTemplate);
                final var path = createPath(result);
                if (fileTemplate.endsWith(".vm")) {
                    putFile(path, "/template/" + fileTemplate);
                } else {
                    putFile(path, SimpleTemplateGenerator.class.getResource("/template/" + fileTemplate));
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private @NonNull Path createPath(String pathAsString) {
            Path result = this.outputPath.resolve(artifactId);
            String[] tokens = pathAsString.split("/");
            for (String token : tokens) {
                result = result.resolve(token);
            }
            return result;
        }

        private @NonNull String prepareTarget(@NonNull String fileTemplate) {
            String result = fileTemplate;
            if (fileTemplate.endsWith(".vm")) {
                result = fileTemplate.substring(0,fileTemplate.length()-".vm".length());
            }
            if (result.contains("groupId")) {
                result = result.replaceAll("groupId",this.groupId.replaceAll("\\.","/"));
            }
            return result;
        }

        private void readStructureFile() throws IOException {
            final var builder = ImmutableList.<String>builder();
            try (BufferedReader is = new BufferedReader(new InputStreamReader(SimpleTemplateGenerator.class.getResourceAsStream("/template/structure.txt")))){
                String line;
                while((line = is.readLine()) != null) {
                    builder.add(line);
                }
            }
            this.structure = builder.build();

        }

        private void createOutputPath() throws IOException {
            this.outputPath = Files.createTempDirectory("perobobbot_plugin");
        }

        private void prepareParent(@NonNull Path path) throws IOException {
            final var parent = path.getParent();
            if (Files.isDirectory(parent)) {
                return;
            }
            Files.createDirectories(parent);
        }

        private void putFile(@NonNull Path path, @NonNull URL source) throws IOException {
            this.prepareParent(path);
            try (InputStream is = source.openStream()) {
                Files.copy(is,path);
            }
        }

        private void putFile(@NonNull Path path, @NonNull String templateName) throws IOException {
            this.prepareParent(path);
            try(BufferedWriter write = Files.newBufferedWriter(path)) {
                output(templateName, context, write);
            }
        }

        private void output(@NonNull String template, @NonNull VelocityContext context, @NonNull Writer writer) throws IOException {
            Velocity.mergeTemplate(template, StandardCharsets.UTF_8.name(), context, writer);
        }

        private void createContext() {
            this.context = new VelocityContext();
            context.put("dependencies", bom.getDependencies());
            context.put("artifactId", artifactId);
            context.put("groupId", groupId);
            final var s = serviceProvider.streamAllServices().map(PluginInfo.ServiceWrapper::new).collect(
                    ImmutableList.toImmutableList());
            context.put("plugin", new PluginInfo(applicationVersion, groupId, groupId, s));
        }
    }
}
