package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientFactory {

    @NonNull WebClient create();

}
