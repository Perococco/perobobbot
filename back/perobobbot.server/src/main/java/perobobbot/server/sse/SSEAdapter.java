package perobobbot.server.sse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.*;
import perobobbot.server.sse.transformer.ApplicationEventTransformer;
import perobobbot.server.sse.transformer.EventTransformer;
import perobobbot.server.sse.transformer.MessageTransformer;
import perobobbot.server.sse.transformer.NotificationTransformer;

/**
 * Adapter to convert application messages to Server Side Events
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class SSEAdapter {

    private final @NonNull EventBuffer eventBuffer;

    private final @NonNull MessageToSSEventTransformer messageToSSEventTransformer;


    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_MESSAGES)
    public void onChatMessage(@NonNull MessageContext messageContext) {
        handleMessage(messageContext);
    }

    @ServiceActivator(inputChannel = GatewayChannels.EVENT_MESSAGES)
    public void onChatMessage(@NonNull ApplicationEvent applicationEvent) {
        handleMessage(applicationEvent);
    }

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    public void onChatMessage(@NonNull Notification notification) {
        handleMessage(notification);
    }



    private void handleMessage(@NonNull Object message) {
        try {
            final var event = messageToSSEventTransformer.transform(message);
            eventBuffer.addMessage(event);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Fail to send message {}", message, t);
        }
    }



}
