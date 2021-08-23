package perobobbot.server.plugin.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jplugman.api.Version;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import perobobbot.lang.TemplateGenerator;
import perobobbot.server.plugin.Bom;
import perobobbot.server.plugin.BotVersionedServices;
import perobobbot.template.StructureEntry;
import perobobbot.template.Templates;

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

    private static final ImmutableSet<String> REQUIRED_MODULE_NAMES = ImmutableSet.of(
            "perobobbot.plugin",
            "perobobbot.extension",
            "perobobbot.access",
            "perobobbot.command"
    );


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
    private final @NonNull BotVersionedServices botVersionedServices;

    @Override
    public @NonNull Path generate(@NonNull String type, @NonNull String groupId, @NonNull String artifactId) throws IOException {
        return new Executor(type, groupId, artifactId).generate();
    }

    @RequiredArgsConstructor
    private class Executor {

        private final @NonNull String type;
        private final @NonNull String groupId;
        private final @NonNull String artifactId;

        private Path outputPath;
        private Templates templates;

        private VelocityContext context;

        public @NonNull Path generate() throws IOException {
            init();
            createContext();
            createOutputPath();
            readStructureFile();

            templates.forEach(type, this::performCopy);

            return outputPath;
        }

        private void performCopy(StructureEntry structureEntry) {
            try {
                final var result = structureEntry.prepareTarget(this.groupId);
                final var path = createPath(result);


//                System.out.format("%s %s %n", structureEntry.getResourcePath(), result);
                if (structureEntry.isVelocityResource()) {
                    putFile(path, structureEntry.getResourcePath());
                } else {
                    final var resource = structureEntry.getResource();
                    putFile(path, resource);
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

        private void readStructureFile() throws IOException {
            final var structureUrl = SimpleTemplateGenerator.class.getResource("/template/structure.txt");
            this.templates = Templates.read();
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
                Files.copy(is, path);
            }
        }

        private void putFile(@NonNull Path path, @NonNull String templateName) throws IOException {
            this.prepareParent(path);
            try (BufferedWriter write = Files.newBufferedWriter(path)) {
                output(templateName, context, write);
            }
        }

        private void output(@NonNull String template, @NonNull VelocityContext context, @NonNull Writer writer) {
            Velocity.mergeTemplate(template, StandardCharsets.UTF_8.name(), context, writer);
        }

        private void createContext() {
            this.context = new VelocityContext();
            context.put("dependencies", bom.getDependencies());
            context.put("artifactId", artifactId);
            context.put("groupId", groupId);
            final var s = botVersionedServices.getServices()
                                              .stream().map(PluginInfo.ServiceWrapper::new)
                                              .collect(ImmutableList.toImmutableList());
            context.put("plugin", new PluginInfo(applicationVersion, groupId, groupId, REQUIRED_MODULE_NAMES,s));
        }
    }
}
