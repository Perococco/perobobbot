package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.lang.*;

@Component
@PluginService(type = NotificationDispatcher.class, apiVersion = NotificationDispatcher.VERSION)
public class SpringNotificationDispatcher implements NotificationDispatcher {

    private Listeners<NotificationListener> listeners = new Listeners<>();

    @Override
    public @NonNull Subscription addListener(@NonNull NotificationListener messageListener) {
        return listeners.addListener(messageListener);
    }

    @ServiceActivator(inputChannel = GatewayChannels.PLATFORM_MESSAGES)
    public void handleNotification(@NonNull Notification notification) {
        listeners.warnListeners(l -> l.onMessage(notification));
    }

}
