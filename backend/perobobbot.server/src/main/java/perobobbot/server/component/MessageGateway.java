package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import perobobbot.lang.MessageContext;
import perobobbot.server.GatewayChannels;

@Component
@MessagingGateway
public interface MessageGateway {

    @Gateway(requestChannel = GatewayChannels.PLATFORM_MESSAGES)
    void sendMessage(@NonNull MessageContext messageContext);
}
