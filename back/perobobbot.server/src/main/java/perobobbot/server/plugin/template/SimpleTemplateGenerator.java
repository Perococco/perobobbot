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
        private Path projectDirectory;
        private Path javaSrcDirectory;
        private Path packageDirectory;

        private VelocityContext context;

        public @NonNull Path generate() throws IOException {
            init();
            createContext();
            createOutputPath();
            initializeDirectories();
            createProjectDirectory();
            putLombokConfigFile();
            putPomFile();
            putGitIgnoreFile();
            putAssemblyFile();
            createJavaSrcDirectory();
            putModuleInfo();
            createPackageDirectory();

            putVersionsClass();
            putMyPluginClass();

            return outputPath;
        }

        private void putVersionsClass() throws IOException {
            putFile(packageDirectory.resolve("Versions.java"),"template/Versions.java.vm");
        }

        private void putMyPluginClass() throws IOException {
            putFile(packageDirectory.resolve("MyPlugin.java"),"/template/MyPlugin.java.vm");
        }

        private void createPackageDirectory() throws IOException {
            Files.createDirectories(this.packageDirectory);
        }

        private void initializeDirectories() {
            this.projectDirectory = outputPath.resolve(artifactId);
            this.javaSrcDirectory = this.projectDirectory.resolve("src").resolve("main").resolve("java");
            Path acc = javaSrcDirectory;
            for (String s : groupId.split("\\.")) {
                acc = acc.resolve(s);
            }
            this.packageDirectory = acc;
        }

        private void createOutputPath() throws IOException {
            this.outputPath = Files.createTempDirectory("perobobbot_plugin");
        }

        private void createProjectDirectory() throws IOException {
            Files.createDirectory(projectDirectory);
        }

        private void putLombokConfigFile() throws IOException {
            putFile(projectDirectory.resolve("lombok.config"),SimpleTemplateGenerator.class.getResource("/template/lombok.config"));
        }


        private void putPomFile() throws IOException {
            putFile(this.projectDirectory.resolve("pom.xml"),"template/pom.xml.vm");
        }

        private void putAssemblyFile() throws IOException {
            final var assemblyPath = projectDirectory.resolve("src").resolve("main").resolve("assembly");
            Files.createDirectories(assemblyPath);
            putFile(assemblyPath.resolve("tozip.xml"),SimpleTemplateGenerator.class.getResource("/template/tozip.xml"));
        }


        private void putGitIgnoreFile() throws IOException {
            putFile(this.projectDirectory.resolve(".gitignore"),  SimpleTemplateGenerator.class.getResource("/template/.gitignore"));
        }

        private void createJavaSrcDirectory() throws IOException {
            Files.createDirectories(javaSrcDirectory);
        }

        private void putModuleInfo() throws IOException {
            putFile(javaSrcDirectory.resolve("module-info.java"),"template/module-info.java.vm");
        }




        private void putFile(@NonNull Path path, @NonNull URL source) throws IOException {
            try (InputStream is = source.openStream()) {
                Files.copy(is,path);
            }
        }

        private void putFile(@NonNull Path path, @NonNull String templateName) throws IOException {
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
