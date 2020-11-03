package perobobbot.server.config;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import perobobbot.common.lang.MessageContext;

@Component
@MessagingGateway
public interface MessageGateway {

    @Gateway(requestChannel = "chatChannel")
    void sendMessage(@NonNull MessageContext messageContext);
}
