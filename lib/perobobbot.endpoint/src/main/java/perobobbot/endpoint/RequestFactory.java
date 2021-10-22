package perobobbot.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;
import perobobbot.data.service.UserService;
import perobobbot.lang.UserAuthenticator;
import perococco.endpoint.BasicJsonRequestFactory;

public interface RequestFactory {

    @NonNull HttpRequestHandler basic(@NonNull HttpMethod method, @NonNull EndPoint<?> endPoint);

    @NonNull HttpRequestHandler secured(@NonNull HttpMethod method, @NonNull SecuredEndPoint<?> securedEndPoint);

    static @NonNull RequestFactory jsonBased(
            @NonNull ObjectMapper objectMapper,
            @NonNull UserAuthenticator userAuthenticator,
            @NonNull UserService userService) {
        return new BasicJsonRequestFactory(objectMapper, userAuthenticator, userService);
    }

}
