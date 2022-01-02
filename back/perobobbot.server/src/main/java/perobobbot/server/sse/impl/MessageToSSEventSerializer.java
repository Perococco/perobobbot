package perobobbot.server.sse.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import perobobbot.server.sse.MessageToSSEventTransformer;
import perobobbot.server.sse.SSEvent;
import perobobbot.server.sse.SSEventAccess;
import perobobbot.server.sse.transformer.DefaultPayloadConstructor;
import perobobbot.server.sse.transformer.EventTransformer;

import java.util.Optional;

@Component
@Log4j2
public class MessageToSSEventSerializer implements MessageToSSEventTransformer {

    private final @NonNull ImmutableList<EventTransformer<?>> eventTransformers;

    private final @NonNull DefaultPayloadConstructor payloadConstructor;

    public MessageToSSEventSerializer(@NonNull ApplicationContext applicationContext, @NonNull DefaultPayloadConstructor payloadConstructor) {
        this.eventTransformers = applicationContext.getBeansOfType(EventTransformer.class)
                                                   .values()
                                                   .stream()
                                                   .<EventTransformer<?>>map(o -> o)
                                                   .collect(ImmutableList.toImmutableList());

        this.payloadConstructor = payloadConstructor;
    }

    @Override
    public @NonNull SSEvent transform(@NonNull Object event) {
        return eventTransformers.stream()
                                .map(e -> tryTransform(e, event))
                                .flatMap(Optional::stream)
                                .findAny()
                                .orElseGet(() -> defaultTransform(event));
    }

    private SSEvent defaultTransform(Object event) {
        return new SSEvent(SSEventAccess.PERMIT_ALL, "message", payloadConstructor.createPayload(event));
    }


    private <T> @NonNull Optional<SSEvent> tryTransform(@NonNull EventTransformer<T> eventTransformer, @NonNull Object event) {
        final var eventType = eventTransformer.getEventType();
        if (eventType.isInstance(event)) {
            return Optional.of(eventTransformer.transform(eventType.cast(event)));
        }
        return Optional.empty();
    }

}
