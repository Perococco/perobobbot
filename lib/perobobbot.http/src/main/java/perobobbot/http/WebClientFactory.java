package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.fp.Consumer1;
import perococco.perobobbot.http.WebClientFactoryBuilder;

import java.util.Collection;
import java.util.Map;

public interface WebClientFactory {

    @NonNull WebClient create();

    default @NonNull WebClient.RequestHeadersSpec<?> get(String uri, Map<String, Collection<?>> queryParams) {
        return create().get().uri(uri, ub -> {
            queryParams.forEach(ub::queryParam);
            return ub.build();
        });
    }

    default @NonNull WebClient.RequestHeadersSpec<?> get(String uri) {
        return create().get().uri(uri);
    }

    default @NonNull WebClient.RequestBodySpec post(String uri) {
        return create().post().uri(uri);
    }

    default @NonNull WebClient.RequestBodySpec post(String uri, Map<String, Collection<?>> queryParams) {
        return create().post().uri(uri, ub -> {
            queryParams.forEach(ub::queryParam);
            return ub.build();
        });
    }

    default @NonNull WebClient.RequestBodySpec post(String uri, String name, Object value) {
        return create().post().uri(uri, ub -> {
            ub.queryParam(name,value);
            return ub.build();
        });
    }

    default @NonNull WebClient.RequestHeadersSpec<?> delete(String uri) {
        return create().delete().uri(uri);
    }

    default @NonNull WebClient.RequestHeadersSpec<?> delete(String uri, Map<String, Collection<?>> queryParams) {
        return create().delete().uri(uri, ub -> {
            queryParams.forEach(ub::queryParam);
            return ub.build();
        });
    }


    default @NonNull WebClient.RequestBodySpec patch(String uri, Map<String, Collection<?>> queryParams) {
        return create().patch().uri(uri, ub -> {
            queryParams.forEach(ub::queryParam);
            return ub.build();
        });
    }



    static @NonNull WebClientFactory.Builder builder(@NonNull WebClient reference) {
        return new WebClientFactoryBuilder(reference);
    }


    @NonNull WebClientFactory.Builder mutate();

    interface Builder {
        @NonNull WebClientFactory build();

        @NonNull Builder addModifier(@NonNull Consumer1<? super WebClient.Builder> modifier);

        @NonNull Builder baseUrl(@NonNull String baseUrl);
    }
}
