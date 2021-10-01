package perobobbot.server.sse.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;

@RequiredArgsConstructor
public class FixedSseEventBuilder implements SseEmitter.SseEventBuilder {

    private final Set<ResponseBodyEmitter.DataWithMediaType> events;

    @Override
    public SseEmitter.SseEventBuilder id(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SseEmitter.SseEventBuilder name(String eventName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SseEmitter.SseEventBuilder reconnectTime(long reconnectTimeMillis) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SseEmitter.SseEventBuilder comment(String comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SseEmitter.SseEventBuilder data(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SseEmitter.SseEventBuilder data(Object object, MediaType mediaType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ResponseBodyEmitter.DataWithMediaType> build() {
        return events;
    }
}
