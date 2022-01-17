package perobobbot.discord.oauth.impl.builder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import perobobbot.discord.oauth.api.DiscordScope;
import perobobbot.discord.oauth.impl.DiscordRequest;
import perobobbot.discord.oauth.impl.DiscordRequestBuilder;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.ImmutableEntry;
import perobobbot.lang.Scope;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DiscordRequestBuilderBase<B extends DiscordRequestBuilderBase<B>> implements DiscordRequestBuilder<B> {

    private final HttpHeaders headers = new HttpHeaders();

    @Getter(AccessLevel.PROTECTED)
    private DecryptedClient client;

    private String baseUri;

    private ImmutableSet<DiscordScope> scopes = ImmutableSet.of();

    @NonNull
    @Override
    public B setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return getThis();
    }

    @Override
    public @NonNull B setClient(@NonNull DecryptedClient client) {
        this.client = client;
        return getThis();
    }

    @Override
    public @NonNull B setScopes(@NonNull ImmutableSet<DiscordScope> scopes) {
        this.scopes = scopes;
        return getThis();
    }

    @Override
    public @NonNull DiscordRequest build() {
        final var uri = URI.create(this.baseUri);

        final var headers = new HttpHeaders();
        headers.putAll(this.headers);
        completeHeaders(headers);

        return this.formData()
                   .map(data -> DiscordRequest.withData(uri, headers, data))
                   .orElseGet(() -> DiscordRequest.withoutData(uri, headers));
    }

    protected abstract B getThis();

    protected abstract void completeHeaders(HttpHeaders headers);

    protected abstract ImmutableMap<String, String> getExtraData();


    private @NonNull Optional<String> formData() {
        final var data = Stream.concat(
                                       getExtraData().entrySet().stream(),
                                       formScopeData().stream()
                               )
                               .map(e -> e.getKey() + "=" + e.getValue())
                               .collect(Collectors.joining("&"));

        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(data);
    }

    private Optional<ImmutableEntry<String, String>> formScopeData() {
        if (this.scopes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ImmutableEntry.of("scope", Scope.scopeNamesSpaceSeparated(this.scopes)));
    }

}
