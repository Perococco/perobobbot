package perobobbot.endpoint;

import lombok.NonNull;
import org.springframework.web.servlet.HandlerMapping;
import perococco.endpoint.SimpleHandlerMappingBuilder;

public interface HandlerMappingBuilder {

    <I> @NonNull HandlerMappingBuilder post(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint);

    <I> @NonNull HandlerMappingBuilder get(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint);

    <I> @NonNull HandlerMappingBuilder put(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint);

    <I> @NonNull HandlerMappingBuilder post(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint);

    <I> @NonNull HandlerMappingBuilder get(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint);

    <I> @NonNull HandlerMappingBuilder put(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint);

    @NonNull HandlerMapping build();

    static @NonNull HandlerMappingBuilder create(@NonNull RequestFactory requestFactory) {
        return new SimpleHandlerMappingBuilder(requestFactory);
    }

}
