package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.Instants;
import perobobbot.lang.PluginService;

import java.time.Instant;

@Component
@PluginService(version = Instants.VERSION, type = Instants.class)
public class PerobobbotInstants implements Instants {

    @Override
    public @NonNull Instant now() {
        return Instant.now();
    }
}
