package perobobbot.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
@Import(DataConfiguration.class)
@Log4j2
public class BotServer {


    public static void main(String[] args) {
        final SpringLauncher launcher = new SpringLauncher(
                Arrays.asList(args),
                BotServer.class,
                new ApplicationContextInitializer[0],
                Optional::of
        );

        launcher.launch();
    }


}
