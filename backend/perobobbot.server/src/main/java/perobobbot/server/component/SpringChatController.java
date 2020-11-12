package perobobbot.server.component;


import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.ProxyChatController;
import perobobbot.lang.MessageContext;

@Component
public class SpringChatController extends ProxyChatController {

    public SpringChatController() {
        super(ChatController.create());
    }

    @ServiceActivator(inputChannel = "chatChannel")
    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        super.handleMessage(messageContext);
    }

}
