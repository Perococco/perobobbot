package perococco.perobobbot.fxspring;

import javafx.application.Platform;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.fx.ControllerFactory;
import perobobbot.fx.FXLoaderFactory;
import perobobbot.fx.FXLoaderFactoryWithControllerFactory;
import perobobbot.i18n.Dictionary;
import perobobbot.lang.ApplicationCloser;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@RequiredArgsConstructor
public class FXSpringConfiguration {

    @NonNull
    private final ApplicationContext applicationContext;

    @NonNull
    private final Dictionary dictionary;


    @Bean
    public FXLoaderFactory fxLoader() {
        return new FXLoaderFactoryWithControllerFactory(controllerFactory(), dictionary, l -> l);
    }

    @Bean
    public ControllerFactory controllerFactory() {
        return new SpringControllerFactory(applicationContext);
    }

}
