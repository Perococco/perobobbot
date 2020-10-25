package perobobbot.server.config;


import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.ChatController;
import perobobbot.chat.core.ProxyChatController;
import perobobbot.common.lang.MessageContext;

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
