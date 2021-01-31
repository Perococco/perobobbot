package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.InstantProvider;

import java.time.Instant;

@Component
public class PerobobbotInstantProvider implements InstantProvider {

    @Override
    public @NonNull Instant getNow() {
        return Instant.now();
    }
}
