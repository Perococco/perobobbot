package perobobbot.server.config;


import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.common.lang.MessageContext;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.ProxyChatController;

@Component
@Service
public class SpringChatController extends ProxyChatController {

    public SpringChatController() {
        super(ChatController.builder().setCommandPrefix('!').build());
    }

    @ServiceActivator(inputChannel = "chatChannel")
    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        super.handleMessage(messageContext);
    }

}
