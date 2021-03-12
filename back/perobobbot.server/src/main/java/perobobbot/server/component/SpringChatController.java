package perobobbot.server.component;


import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageDispatcher;
import perobobbot.messaging.ChatController;
import perobobbot.messaging.ProxyChatController;
import perobobbot.lang.PluginService;

@Component
@PluginService(type = MessageDispatcher.class, version = MessageDispatcher.VERSION)
public class SpringChatController extends ProxyChatController {

    public SpringChatController() {
        super(ChatController.create());
    }

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_MESSAGES)
    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        super.handleMessage(messageContext);
    }

}
