package perobobbot.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.integration.config.EnablePublisher;
import perobobbot.spring.SpringLauncher;

import java.util.Arrays;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "file:${app.config.dir}/server/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${app.config.dir}/server/database_secret.properties", ignoreResourceNotFound = false)
})
@Log4j2
@EnablePublisher
public class BotServer {


    public static void main(String[] args) {
        final var launcher = new SpringLauncher(
                Arrays.asList(args),
                BotServer.class,
                new ApplicationContextInitializer[]{},
                p -> true
        );

        launcher.launch();
    }

}
