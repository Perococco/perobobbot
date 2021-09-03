package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import perobobbot.lang.*;

@Component
@MessagingGateway
public interface SpringMessageGateway extends MessageGateway {

    @Gateway(requestChannel = GatewayChannels.PLATFORM_MESSAGES)
    void sendPlatformMessage(@NonNull MessageContext messageContext);

    @Gateway(requestChannel = GatewayChannels.EVENT_MESSAGES)
    void sendEvent(@NonNull ApplicationEvent applicationEvent);

    @Gateway(requestChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    void sendPlatformNotification(@NonNull Notification event);

}
