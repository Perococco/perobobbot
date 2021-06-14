package perobobbot.server;

import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.Event;
import perobobbot.lang.GatewayChannels;

@Component
public class NotificationTest {

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    public void onNotification(@NonNull Event event) {
        System.out.println("Notification "+event);
    }
}
