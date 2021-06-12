package perococco.perobobbot.http;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.ListTool;
import perobobbot.lang.fp.Consumer1;

@RequiredArgsConstructor
public class WebClientFactoryBuilder implements WebClientFactory.Builder {

    private final @NonNull WebClient reference;

    private @NonNull ImmutableList<Consumer1<? super WebClient.Builder>> modifiers = ImmutableList.of();

    protected WebClientFactoryBuilder(@NonNull WebClient reference, @NonNull ImmutableList<Consumer1<? super WebClient.Builder>> modifiers) {
        this.reference = reference;
        this.modifiers = modifiers;
    }

    @Override
    public WebClientFactory.@NonNull Builder baseUrl(@NonNull String baseUrl) {
        return addModifier(b -> b.baseUrl(baseUrl));
    }

    @Override
    public @NonNull WebClientFactory build() {
        return new WebClientFactoryWithModifiers(reference,modifiers);
    }

    @Synchronized
    @Override
    public @NonNull WebClientFactory.Builder addModifier(@NonNull Consumer1<? super WebClient.Builder> modifier) {
        this.modifiers = ListTool.addLast(this.modifiers,modifier);
        return this;
    }
}
