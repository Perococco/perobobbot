package perobobbot.fxspring;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContextInitializer;
import perobobbot.fx.FXProperties;
import perococco.perobobbot.fxspring.SpringLauncher;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Plugin;
import perobobbot.lang.fp.TryResult;
import perobobbot.spring.AddSingletonToApplicationContext;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Log4j2
@RequiredArgsConstructor
public abstract class FXSpringApplication extends Application {

    @NonNull
    private final Class<?> applicationClass;

    private ApplicationCloser contextCloser = () -> {};

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            beforeLaunchingSpring(primaryStage);
            this.launchSpring(new AddSingletonToApplicationContext("fxProperties", createFxProperties(primaryStage)))
                .whenComplete((r,t) -> handleSpringLaunchCompletion(primaryStage,r,t));
        } catch (Throwable e) {
            LOG.error("Launch failed ",e);
            try {
                contextCloser.execute();
            } catch (Throwable se) {
                e.addSuppressed(se);
            }
            throw e;
        }
    }

    @NonNull
    protected Optional<Plugin> processPluginBeforeIncludingThemIntoSpring(@NonNull Plugin plugins) {
        return Optional.of(plugins);
    }

    @NonNull
    private FXProperties createFxProperties(Stage primaryStage) {
        return new FXProperties(primaryStage, getHostServices());
    }

    private void handleSpringLaunchCompletion(@NonNull Stage primaryStage, ApplicationCloser applicationCloser, Throwable error) {
        final TryResult<Throwable,ApplicationCloser> result = error==null?TryResult.success(applicationCloser):TryResult.failure(error);
        this.afterLaunchingSpring(primaryStage,result);

        result.acceptIfFailure(e -> {
            LOG.error("Launch failed ",e);
            Platform.exit();
            System.exit(1);
        });

        this.contextCloser = result.getOr(this.contextCloser);
    }


    @NonNull
    private CompletionStage<ApplicationCloser> launchSpring(
            ApplicationContextInitializer<?>... initializers) {
        final SpringLauncher springLauncher = new SpringLauncher(getParameters().getRaw(),
                                                                 applicationClass,
                                                                 initializers,
                                                                 this::processPluginBeforeIncludingThemIntoSpring);
        return CompletableFuture.supplyAsync(springLauncher::launch);
    }


    protected void beforeLaunchingSpring(@NonNull Stage primaryStage) throws Exception {}

    protected void afterLaunchingSpring(
            @NonNull Stage primaryStage,
            @NonNull TryResult<Throwable,ApplicationCloser> result) {};
}
