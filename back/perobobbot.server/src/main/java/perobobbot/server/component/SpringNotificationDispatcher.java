package perobobbot.server.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.*;

@Component
@RequiredArgsConstructor
@PluginService(type = NotificationDispatcher.class, apiVersion = NotificationDispatcher.VERSION, sensitive = false)
public class SpringNotificationDispatcher implements NotificationDispatcher {

    private final Listeners<NotificationListener> listeners = new Listeners<>();
    private final @NonNull MessageGateway messageGateway;

    @Override
    public @NonNull Subscription addListener(@NonNull NotificationListener messageListener) {
        return listeners.addListener(messageListener);
    }

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    public void handleNotification(@NonNull Notification notification) {
        listeners.warnListeners(l -> l.onMessage(notification));
    }

    @Override
    public void sendNotification(@NonNull Notification notification) {
        messageGateway.sendPlatformNotification(notification);
    }
}
