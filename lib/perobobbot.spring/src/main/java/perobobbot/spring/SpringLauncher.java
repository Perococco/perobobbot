package perobobbot.spring;

import com.google.common.collect.ImmutableList;
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
import perobobbot.lang.Packages;
import perobobbot.lang.fp.Predicate1;

import java.awt.*;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author perococco
 */
@Log4j2
@RequiredArgsConstructor
public class SpringLauncher {

    private final String applicationName = "perobobbot";


    private final @NonNull List<String> arguments;

    private final @NonNull Class<?>[] applicationClasses;

    private final @NonNull ApplicationContextInitializer<?>[] initializers;

    private final @NonNull Predicate1<? super Packages> packagesFilter;

    private final @NonNull Banner.Mode bannerMode;

    @NonNull
    public ApplicationCloser launch() {
        return new Execution().launch();
    }

    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super Packages> packagesFilter,
                          @NonNull Banner.Mode bannerMode) {
        this(arguments, new Class<?>[]{applicationClass}, initializers, packagesFilter, bannerMode);
    }

    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super Packages> packagesFilter) {
        this(arguments, new Class<?>[]{applicationClass}, initializers, packagesFilter, Banner.Mode.CONSOLE);
    }

    private class Execution {

        private SpringApplication application;

        private String[] extraPackagesToScan;

        private ApplicationCloser closer;

        private ImmutableList<Packages> packagesList;

        private ApplicationCloser launch() {
            this.setupConfigDirectory();
            this.loadAllPackages();
            this.createSpringApplication();
            this.retrieveAllExtraPackagesToScan();
            this.setupSpringApplicationInitializerToTakeIntoAccountExtraPackages();
            this.launchTheApplicationAndConstructTheCloser();
            return closer;
        }

        private void setupConfigDirectory() {
            final var configDirectory = OSInfo.INSTANCE.getConfigDirectory();
            final var appConfigDirectory = OSInfo.INSTANCE.getAppConfigDirectory(applicationName);
            setSystemProperty("config.dir", configDirectory);
            setSystemProperty("app.config.dir", appConfigDirectory);
            setSystemProperty("app.plugin.dir", Path.of(appConfigDirectory).resolve("plugins").toAbsolutePath().toString());
        }

        private void setSystemProperty(String propertyName, String value) {
            final var existing = System.getProperty(propertyName);
            if (existing == null) {
                System.setProperty(propertyName,value);
            }
        }

        private void loadAllPackages() {
            this.packagesList = ServiceLoader.load(Packages.class)
                                             .stream()
                                             .map(ServiceLoader.Provider::get)
                                             .collect(ImmutableList.toImmutableList());
            if (LOG.isInfoEnabled()) {
                this.packagesList
                        .stream()
                        .sorted(Comparator.comparing(Packages::getName))
                        .forEach(p -> LOG.info("Module : [{}] {}", p.getClass().getSimpleName(), p.getName()));
            }
        }

        private void createSpringApplication() {
            final boolean headless = GraphicsEnvironment.isHeadless();
            application = new SpringApplication(applicationClasses);
            application.setHeadless(headless);
            application.addInitializers(app -> {
                app.getBeanFactory().registerSingleton("__closer", createCloser(app));
            });
            application.addInitializers(initializers);
            application.setBannerMode(bannerMode);
        }

        private void retrieveAllExtraPackagesToScan() {
            extraPackagesToScan = packagesList.stream()
                                              .filter(packagesFilter)
                                              .flatMap(Packages::packageStream)
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
            return new SpringApplicationCloser(context);

        }

    }

}
