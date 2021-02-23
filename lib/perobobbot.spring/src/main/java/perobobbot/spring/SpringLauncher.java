package perobobbot.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.OSInfo;
import perobobbot.plugin.*;
import perobobbot.lang.fp.Predicate1;

import java.awt.*;
import java.util.List;

/**
 * @author Perococco
 */
@Log4j2
@RequiredArgsConstructor
public class SpringLauncher {


    @NonNull
    private final List<String> arguments;

    private final String applicationName = "perobobbot";

    @NonNull
    private final Class<?>[] applicationClasses;

    @NonNull
    private final ApplicationContextInitializer<?>[] initializers;

    @NonNull
    private final Predicate1<? super FunctionalPlugin> pluginFilter;

    private final Banner.Mode bannerMode;

    @NonNull
    public ApplicationCloser launch() {
        return new Execution().launch();
    }

    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super FunctionalPlugin> pluginFilter,
                          @NonNull Banner.Mode bannerMode) {
        this(arguments, new Class<?>[]{applicationClass}, initializers, pluginFilter, bannerMode);
    }

    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super FunctionalPlugin> pluginFilter) {
        this(arguments, new Class<?>[]{applicationClass}, initializers, pluginFilter, Banner.Mode.CONSOLE);
    }

    private class Execution {

        private SpringApplication application;

        private String[] extraPackagesToScan;

        private ApplicationCloser closer;

        private PluginList pluginList;

        private ApplicationCloser launch() {
            this.setupConfigDirectory();
            this.loadAllPlugins();
            this.createSpringApplication();
            this.retrieveAllExtraPackagesToScan();
            this.setupSpringApplicationInitializerToTakeIntoAccountExtraPackages();
            this.launchTheApplicationAndConstructTheCloser();
            return closer;
        }

        private void setupConfigDirectory() {
            System.setProperty("config.dir", OSInfo.INSTANCE.getConfigDirectory());
            System.setProperty("app.config.dir", OSInfo.INSTANCE.getAppConfigDirectory(applicationName));
        }

        private void loadAllPlugins() {
            this.pluginList = Plugin.loadAllPlugins();
            if (LOG.isInfoEnabled()) {
                this.pluginList.streamAllPlugins()
                               .sorted(Plugin.COMPARE_NAME)
                               .forEach(p -> LOG.info("Plugin : [{}] {}", p.getClass().getSimpleName(), p.getName()));
            }
        }

        private void createSpringApplication() {
            final boolean headless = GraphicsEnvironment.isHeadless();
            application = new SpringApplication(applicationClasses);
            application.setHeadless(headless);
            application.addInitializers(app -> {
                app.getBeanFactory().registerSingleton("__closer", createCloser(app));
                app.getBeanFactory().registerSingleton("__pluginList", this.pluginList);
            });
            application.addInitializers(initializers);
            application.setBannerMode(bannerMode);
        }

        private void retrieveAllExtraPackagesToScan() {
            extraPackagesToScan = pluginList.streamFunctionalPlugins()
                                            .filter(pluginFilter)
                                            .flatMap(FunctionalPlugin::packageStream)
                                            .toArray(String[]::new);
        }

        private void setupSpringApplicationInitializerToTakeIntoAccountExtraPackages() {
            if (extraPackagesToScan.length == 0) {
                return;
            }
            application.addInitializers(c -> {
                if (c instanceof AnnotationConfigRegistry) {
                    ((AnnotationConfigRegistry) c).scan(extraPackagesToScan);
                } else {
                    LOG.error(
                            "Could not add extra package to scans. Context is not an AnnotationConfigRegistry : context.class={}",
                            c.getClass());
                }
            });
        }

        private void launchTheApplicationAndConstructTheCloser() {
            final ApplicationContext app = application.run(arguments.toArray(String[]::new));
            this.closer = createCloser(app);
        }

        private ApplicationCloser createCloser(@NonNull ApplicationContext context) {
            return () -> {
                final int exitCode = SpringApplication.exit(context);
                System.exit(exitCode);
            };

        }

    }

}
