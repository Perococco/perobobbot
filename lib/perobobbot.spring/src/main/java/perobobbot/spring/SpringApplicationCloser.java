package perobobbot.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import perobobbot.lang.ApplicationCloser;
import perobobbot.plugin.PluginService;

@RequiredArgsConstructor
public class SpringApplicationCloser implements ApplicationCloser, PluginService {

    private final  @NonNull ApplicationContext context;

    @Override
    public void execute() {
        final int exitCode = SpringApplication.exit(context);
        System.exit(exitCode);

    }
}
