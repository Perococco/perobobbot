package perobobbot.twitch.eventsub.manager;

import lombok.NonNull;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import perobobbot.lang.GatewayChannels;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

@Component
@MessagingGateway
public interface PlatformNotificationGateway {

    @Gateway(requestChannel = GatewayChannels.PLATFORM_NOTIFICATION_MESSAGES)
    void sendPlatformNotification(@NonNull EventSubEvent event);

}
