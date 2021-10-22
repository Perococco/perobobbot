package perococco.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.HttpRequestHandler;
import perobobbot.endpoint.EndPoint;
import perobobbot.lang.Nil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class BasicJsonRequest<I> implements HttpRequestHandler {

    @Getter
    private final @NonNull HttpMethod method;

    private final @NonNull Supplier<EndPoint<I>> endPointSupplier;

    private final @NonNull ObjectMapper objectMapper;

    @Override
    public void handleRequest(HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        if (!method.matches(request.getMethod())) {
            throw new IllegalArgumentException("Bug in request method mapping");
        }

        final var endPoint = endPointSupplier.get();

        final I body = readBody(endPoint.getBodyType(),request);
        final var result = endPoint.handle(body);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        if (result != null && !(result instanceof Nil)) {
            final var content = objectMapper.writeValueAsString(result);
            response.getWriter().println(content);
        }

    }

    private <I> I readBody(@NonNull Class<I> bodyType, @NonNull HttpServletRequest request) throws IOException {
        final I body;
        if (Void.class.equals(bodyType)) {
            body = null;
        } else if (bodyType.isAssignableFrom(Nil.class)) {
            body = bodyType.cast(Nil.NIL);
        } else {
            body = objectMapper.readValue(request.getReader(), bodyType);
        }
        return body;
    }



}
