package perobobbot.console.spring;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.lang.ApplicationCloser;
import perobobbot.console.ConsoleIO;
import perococco.perobobbot.console.Console;

@Configuration
public class ConsoleConfiguration {

    @Bean(destroyMethod = "disable")
    public ConsoleIO console(@NonNull ApplicationCloser applicationCloser) {
        return new Console(applicationCloser).enable();
    }
}
