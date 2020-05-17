package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import perobobbot.common.lang.ApplicationCloser;
import perobobbot.common.lang.Packages;
import perobobbot.common.lang.fp.Function1;

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
    private final Function1<? super Packages, ? extends Optional<Packages>> packageProcessor;

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
            application = new SpringApplication(applicationClass);
            application.addInitializers(initializers);
            application.setBannerMode(Banner.Mode.OFF);
        }

        private void retrieveAllExtraPackagesToScan() {
            extraPackagesToScan = ServiceLoader.load(Packages.class)
                                               .stream()
                                               .map(ServiceLoader.Provider::get)
                                               .map(packageProcessor)
                                               .flatMap(Optional::stream)
                                               .flatMap(Packages::stream)
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
            closer = () -> SpringApplication.exit(app);
        }

    }

}
