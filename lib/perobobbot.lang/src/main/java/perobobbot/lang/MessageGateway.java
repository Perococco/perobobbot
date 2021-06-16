package perobobbot.lang;

import lombok.NonNull;

public interface MessageGateway {

    void sendPlatformMessage(@NonNull MessageContext messageContext);

    void sendEvent(@NonNull Event event);

    void sendPlatformNotification(@NonNull Notification event);

}
