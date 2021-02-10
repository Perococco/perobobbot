package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.Instants;

import java.time.Instant;

@Component
public class PerobobbotInstants implements Instants {

    @Override
    public @NonNull Instant now() {
        return Instant.now();
    }
}
