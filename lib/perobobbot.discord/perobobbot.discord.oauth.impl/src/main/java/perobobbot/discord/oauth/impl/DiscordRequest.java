package perobobbot.discord.oauth.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscordRequest {

    public @NonNull WebClient.ResponseSpec postWith(@NonNull WebClient webClient) {
        return  webClient.post()
                          .uri(this.getUri())
                          .headers(this::setupHeaders)
                          .body(this.getBodyInserters())
                          .retrieve();
    }


    public static @NonNull DiscordRequest withData(@NonNull URI uri, @NonNull HttpHeaders headers, @NonNull String data) {
        return new DiscordRequest(uri, headers, data);
    }

    public static @NonNull DiscordRequest withoutData(@NonNull URI uri, @NonNull HttpHeaders headers) {
        return new DiscordRequest(uri, headers, null);
    }

    @Getter
    private final @NonNull URI uri;
    private final @NonNull HttpHeaders headers;
    private final String data;


    public void setupHeaders(@NonNull HttpHeaders httpHeaders) {
        httpHeaders.addAll(this.headers);
    }

    public @NonNull Optional<String> getData() {
        return Optional.ofNullable(data);
    }

    public @NonNull BodyInserter<String, ReactiveHttpOutputMessage> getBodyInserters() {
        if (data == null) {
            return BodyInserters.empty();
        } else {
            return BodyInserters.fromValue(data);
        }
    }
}
