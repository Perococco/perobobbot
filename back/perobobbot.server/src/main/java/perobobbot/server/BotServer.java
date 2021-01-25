package perobobbot.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import perobobbot.lang.fp.Predicate1;
import perobobbot.spring.SpringLauncher;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
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
