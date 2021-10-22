package perococco.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import perobobbot.data.service.UserService;
import perobobbot.endpoint.EndPoint;
import perobobbot.endpoint.RequestFactory;
import perobobbot.endpoint.SecuredEndPoint;
import perobobbot.lang.UserAuthenticator;

@RequiredArgsConstructor
public class BasicJsonRequestFactory implements RequestFactory {

    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull UserAuthenticator userAuthenticator;
    private final @NonNull UserService userService;

    public @NonNull BasicJsonRequest<?> basic(@NonNull HttpMethod method, @NonNull EndPoint<?> endPoint) {
        return new BasicJsonRequest<>(method,() -> endPoint, objectMapper);
    }

    public @NonNull BasicJsonRequest<?> secured(@NonNull HttpMethod method, @NonNull SecuredEndPoint<?> securedEndPoint){
        return new BasicJsonRequest<>(method,() -> createEndPoint(securedEndPoint), objectMapper);
    }

    private <I> @NonNull EndPoint<I> createEndPoint(@NonNull SecuredEndPoint<I> securedEndPoint) {
        final var login = userAuthenticator.authenticatedLogin().orElseThrow(() -> new AccessDeniedException("403 access denied"));
        final var user = userService.getUser(login).simplify();
        return securedEndPoint.createEndPoint(user);
    }


}
