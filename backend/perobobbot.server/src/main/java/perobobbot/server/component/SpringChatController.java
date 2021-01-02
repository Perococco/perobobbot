package perobobbot.server.component;


import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.MessageContext;
import perobobbot.messaging.ChatController;
import perobobbot.messaging.ProxyChatController;
import perobobbot.lang.GatewayChannels;

@Component
public class SpringChatController extends ProxyChatController {

    public SpringChatController() {
        super(ChatController.create());
    }

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_MESSAGES)
    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        super.handleMessage(messageContext);
    }


    @ServiceActivator(inputChannel = GatewayChannels.EVENT_MESSAGES)
    public void toto(@NonNull Object event) {
        System.out.println("###EVENT : "+event);
    }
}
