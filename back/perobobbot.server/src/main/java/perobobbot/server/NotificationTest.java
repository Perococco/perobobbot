package perobobbot.server;

import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.Event;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.Notification;

@Component
public class NotificationTest {

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    public void onNotification(@NonNull Notification notification) {
        System.out.println("Notification "+notification);
    }
}
