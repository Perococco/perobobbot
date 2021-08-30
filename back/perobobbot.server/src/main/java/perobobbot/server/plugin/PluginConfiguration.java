package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Version;
import jplugman.manager.PluginManager;
import jplugman.tools.FolderListener;
import jplugman.tools.FolderWatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.PluginService;
import perobobbot.lang.PluginServices;
import perobobbot.lang.SetTool;
import perobobbot.lang.TemplateGenerator;
import perobobbot.server.config.io.ChatPlatformPluginManager;
import perobobbot.server.plugin.template.SimpleTemplateGenerator;
import perobobbot.server.plugin.webplugin.WebPluginManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class PluginConfiguration {


    @Value("${application.version}")
    private final String rawVersion;

    @Value("${app.plugin.dir}")
    private final Path pluginDir;

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull ExtensionManager extensionManager;
    private final @NonNull @EventService ExtensionService extensionService;
    private final @NonNull ChatPlatformPluginManager chatPlatformPluginManager;
    private final @NonNull WebPluginManager webPluginManager;

    @Bean(destroyMethod = "stop")
    public @NonNull FolderWatcher pluginFolderWatcher() throws NoSuchAlgorithmException, IOException {
        this.extensionService.setAllExtensionAsUnavailable();
        Files.createDirectories(pluginDir.toAbsolutePath());
        final FolderWatcher folderWatcher = FolderWatcher.create(pluginDir.toAbsolutePath());
        final FolderListener pluginListener = new PluginFolderListener(pluginManager());


        folderWatcher.addListener(new FilteringFolderListener(MessageDigest.getInstance("MD5"), pluginListener));
        return new DelayedFolderWatcher(folderWatcher);
    }

    @Bean
    public TemplateGenerator templateGenerator() throws IOException {
        return new SimpleTemplateGenerator(bom(), getApplicationVersion(), versionedServices());
    }

    private @NonNull Bom bom() throws IOException {
        return BomReader.read();
    }

    private PluginManager pluginManager() {
        return PluginManager.create(createApplication());
    }

    @Bean
    public PluginApplication createApplication() {
        return new PluginApplication(getApplicationVersion(),
                                     SetTool.map(versionedServices().getServices(),
                                                 BotVersionedService::toVersionedService),
                                     extensionManager,
                                     webPluginManager,
                                     extensionService,
                                     chatPlatformPluginManager);
    }


    @Bean
    public @NonNull BotVersionedServices versionedServices() {
        final var services =
                Stream.concat(
                        applicationContext.getBeansWithAnnotation(PluginService.class).keySet().stream(),
                        applicationContext.getBeansWithAnnotation(PluginServices.class).keySet().stream()
                ).distinct()
                      .flatMap(this::toVersionedService)
                      .distinct()
                      .collect(ImmutableSet.toImmutableSet());
        return new BotVersionedServices(services);
    }

    private @NonNull Stream<BotVersionedService> toVersionedService(@NonNull String name) {
        return getPluginServices(name)
                .map(ps -> {
                    final Object bean = applicationContext.getBean(name);
                    final Class<?> annotationType = ps.type();
                    final Class<?> type;
                    if (Void.class.equals(annotationType) || !annotationType.isInstance(bean)) {
                        type = bean.getClass();
                    } else {
                        type = annotationType;
                    }
                    return new BotVersionedService(type, bean, ps.apiVersion(), ps.sensitive());
                });
    }

    private @NonNull Stream<PluginService> getPluginServices(@NonNull String beanName) {
        return Stream.concat(
                Stream.of(applicationContext.findAnnotationOnBean(beanName, PluginService.class)),
                Optional.ofNullable(applicationContext.findAnnotationOnBean(beanName, PluginServices.class))
                        .stream()
                        .map(PluginServices::value)
                        .flatMap(Stream::of)
        ).filter(Objects::nonNull);
    }


    private @NonNull Version getApplicationVersion() {
        return VersionFormatter.format(rawVersion);
    }


}
