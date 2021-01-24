package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.MessageContext;

@Component
@MessagingGateway
public interface MessageGateway {

    @Gateway(requestChannel = GatewayChannels.PLATFORM_MESSAGES)
    void sendPlatformMessage(@NonNull MessageContext messageContext);

    @Gateway(requestChannel = GatewayChannels.EVENT_MESSAGES)
    void sendEvent(@NonNull Object event);
}
