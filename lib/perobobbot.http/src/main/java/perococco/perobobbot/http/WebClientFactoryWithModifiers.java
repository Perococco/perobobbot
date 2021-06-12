package perococco.perobobbot.http;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.fp.Consumer1;

@RequiredArgsConstructor
public class WebClientFactoryWithModifiers implements WebClientFactory {

    private final @NonNull WebClient reference;

    private final @NonNull ImmutableList<Consumer1<? super WebClient.Builder>> modifiers;

    @Override
    public @NonNull WebClient create() {
        final var builder = reference.mutate();
        modifiers.forEach(m -> m.accept(builder));
        return builder.build();
    }

    @Override
    public @NonNull WebClientFactory.Builder mutate() {
        return new WebClientFactoryBuilder(reference,modifiers);
    }
}
