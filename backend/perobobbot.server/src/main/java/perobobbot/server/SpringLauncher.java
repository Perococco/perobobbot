package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Plugin;
import perobobbot.lang.fp.Function1;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * @author Perococco
 */
@Log4j2
@RequiredArgsConstructor
public class SpringLauncher {

    @NonNull
    private final List<String> arguments;

    @NonNull
    private final Class<?> applicationClass;

    @NonNull
    private final ApplicationContextInitializer<?>[] initializers;

    @NonNull
    private final Function1<? super Plugin, ? extends Optional<Plugin>> packageProcessor;

    @NonNull
    public ApplicationCloser launch() {
        return new Execution().launch();
    }

    private class Execution {

        private SpringApplication application;

        private String[] extraPackagesToScan;

        private ApplicationCloser closer;

        private ApplicationCloser launch() {
            this.createSpringApplication();
            this.retrieveAllExtraPackagesToScan();
            this.setupSpringApplicationInitializerToTakeIntoAccountExtraPackages();
            this.launchTheApplicationAndConstructTheCloser();
            return closer;
        }

        private void createSpringApplication() {
            final boolean headless = GraphicsEnvironment.isHeadless();
            application = new SpringApplication(applicationClass);
            application.setHeadless(headless);
            application.addInitializers(app -> {
                app.getBeanFactory().registerSingleton("__closer", createCloser(app));
            });
            application.addInitializers(initializers);
            application.setBannerMode(Banner.Mode.OFF);
        }

        private void retrieveAllExtraPackagesToScan() {
            extraPackagesToScan = ServiceLoader.load(Plugin.class)
                                               .stream()
                                               .map(ServiceLoader.Provider::get)
                                               .map(packageProcessor)
                                               .flatMap(Optional::stream)
                                               .sorted(Plugin.TYPE_THEN_NAME)
                                               .peek(p -> LOG.info("Plugin : [{}] {}", p.type(), p.name()))
                                               .flatMap(Plugin::packageStream)
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
