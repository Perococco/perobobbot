package perobobbot.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.PropertySource;
import perobobbot.spring.SpringLauncher;

import java.util.Arrays;

@SpringBootApplication
@PropertySource(value = "file:${app.config.dir}/config/application.properties",ignoreResourceNotFound = true)
@Log4j2
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
