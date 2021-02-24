package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.Instants;
import perobobbot.plugin.PluginService;

import java.time.Instant;

@Component
public class PerobobbotInstants implements Instants, PluginService {

    @Override
    public @NonNull Instant now() {
        return Instant.now();
    }
}
