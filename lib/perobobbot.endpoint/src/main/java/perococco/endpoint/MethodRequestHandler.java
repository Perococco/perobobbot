package perococco.endpoint;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class MethodRequestHandler implements HttpRequestHandler {

    private final ImmutableMap<String, HttpRequestHandler> handlers;

    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        final var handler = handlers.get(httpServletRequest.getMethod());

        if (handler == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            handler.handleRequest(httpServletRequest,httpServletResponse);
        }

        httpServletResponse.flushBuffer();
    }
}
