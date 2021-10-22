package perococco.endpoint;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import perobobbot.endpoint.EndPoint;
import perobobbot.endpoint.HandlerMappingBuilder;
import perobobbot.endpoint.RequestFactory;
import perobobbot.endpoint.SecuredEndPoint;
import perobobbot.lang.MapTool;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleHandlerMappingBuilder implements HandlerMappingBuilder {

    private final @NonNull RequestFactory requestFactory;

    @Getter(AccessLevel.PROTECTED)
    private final Map<String,Map<String, HttpRequestHandler>> handlers = new HashMap<>();

    private <I> @NonNull HandlerMappingBuilder add(@NonNull HttpMethod httpMethod, @NonNull String pathUrl, @NonNull EndPoint<I> endPoint) {
        final var handler = requestFactory.basic(httpMethod,endPoint);
        this.handlers.computeIfAbsent(pathUrl,p -> new HashMap<>()).put(httpMethod.name(),handler);
        return this;
    }

    private <I> @NonNull HandlerMappingBuilder add(@NonNull HttpMethod httpMethod, @NonNull String pathUrl, @NonNull SecuredEndPoint<I> securedEndPoint) {
        final var handler = requestFactory.secured(httpMethod,securedEndPoint);
        this.handlers.computeIfAbsent(pathUrl,p -> new HashMap<>()).put(httpMethod.name(),handler);
        return this;
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder post(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint) {
        return add(HttpMethod.POST,pathUrl,endPoint);
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder get(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint) {
        return add(HttpMethod.GET,pathUrl,endPoint);
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder put(@NonNull String pathUrl, @NonNull EndPoint<I> endPoint) {
        return add(HttpMethod.PUT,pathUrl,endPoint);
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder post(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint) {
        return add(HttpMethod.POST,pathUrl,endPoint);
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder get(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint) {
        return add(HttpMethod.GET,pathUrl,endPoint);
    }

    @Override
    public <I> @NonNull HandlerMappingBuilder put(@NonNull String pathUrl, @NonNull SecuredEndPoint<I> endPoint) {
        return add(HttpMethod.PUT,pathUrl,endPoint);
    }

    @Override
    public @NonNull HandlerMapping build() {
        final var handlers = MapTool.unsafeMapValues(this.handlers,m -> (Object)new MethodRequestHandler(ImmutableMap.copyOf(m)));
        return MyHandlerMapping.create(handlers);
    }

    private static class MyHandlerMapping extends SimpleUrlHandlerMapping {

        public static HandlerMapping create(@NonNull ImmutableMap<String,Object> handlers) {
            final var handlerMapping = new MyHandlerMapping();
            return handlerMapping.init(handlers);
        }

        public MyHandlerMapping init(Map<String, Object> urlMap) throws BeansException {
            this.setUrlMap(urlMap);
            super.registerHandlers(urlMap);
            return this;
        }
    }

}
