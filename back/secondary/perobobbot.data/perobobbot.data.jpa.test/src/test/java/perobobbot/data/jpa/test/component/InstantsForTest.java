package perobobbot.data.jpa.test.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.Instants;

import java.time.Instant;

@Component
public class InstantsForTest implements Instants {

    @Override
    public @NonNull Instant now() {
        return Instant.now();
    }
}
