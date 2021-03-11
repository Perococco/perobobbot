package perobobbot.server.plugin;

import com.google.common.collect.ImmutableList;
import jplugman.api.*;
import jplugman.manager.PluginManager;
import jplugman.tools.FolderWatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.extension.ExtensionManager;
import perobobbot.plugin.PluginService;
import perobobbot.server.config.io.ChatPlatformPluginManager;
import perobobbot.lang.TemplateGenerator;
import perobobbot.server.plugin.template.SimpleTemplateGenerator;
import perobobbot.server.plugin.webplugin.WebPluginManager;

import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class PluginConfiguration {


    @Value("${application.version}")
    private final String rawVersion;

    @Value("${app.config.dir}")
    private final String appDir;

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull ExtensionManager extensionManager;
    private final @NonNull ChatPlatformPluginManager chatPlatformPluginManager;
    private final @NonNull WebPluginManager webPluginManager;

    @Bean(destroyMethod = "stop")
    public @NonNull FolderWatcher pluginFolderWatcher() throws NoSuchAlgorithmException {
        final FolderWatcher folderWatcher = FolderWatcher.create(Path.of(appDir).resolve("plugins"));
        folderWatcher.addListener(new FolderListenerLogger(new PluginFolderListener(MessageDigest.getInstance("MD5"), pluginManager())));
        folderWatcher.start();
        return folderWatcher;
    }

    @Bean
    public TemplateGenerator templateGenerator() throws IOException {
        return new SimpleTemplateGenerator(bom(),getApplicationVersion(),versionedServiceProvider());
    }

    private @NonNull Bom bom() throws IOException {
        return BomReader.read();
    }

    private PluginManager pluginManager() {
        return PluginManager.create(createApplication());
    }

    @Bean
    public PluginApplication createApplication() {
        return new PluginApplication(getApplicationVersion(), versionedServiceProvider(),
                                     extensionManager,
                                     webPluginManager,
                                     chatPlatformPluginManager);
    }


    @Bean
    public @NonNull BotVersionedServiceProvider versionedServiceProvider() {
        final var services = applicationContext.getBeansWithAnnotation(PluginService.class)
                                              .entrySet()
                                              .stream()
                                              .map(e -> toVersionedService(e.getKey(), e.getValue()))
                                              .collect(ImmutableList.toImmutableList());
        return new BotVersionedServiceProvider(services);
    }

    private @NonNull VersionedService toVersionedService(@NonNull String name, @NonNull Object bean) {
        final var pluginService = applicationContext.findAnnotationOnBean(name, PluginService.class);
        assert pluginService != null;
        final Class<?> annotationType = pluginService.type();
        final Class<?> type;
        if (Void.class.equals(annotationType) || !annotationType.isInstance(bean)) {
            type = bean.getClass();
        } else {
            type = annotationType;
        }
        return new VersionedService(type,bean, Version.with(pluginService.version()));
    }


    private @NonNull Version getApplicationVersion() {
        return VersionFormatter.format(rawVersion);
    }


}
