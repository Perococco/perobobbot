package perobobbot.server.config;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import perobobbot.common.lang.MessageContext;

@MessagingGateway
public interface ChatGateway {

    @Gateway(requestChannel = "chatChannel")
    void sendMessage(@NonNull MessageContext messageContext);
}
