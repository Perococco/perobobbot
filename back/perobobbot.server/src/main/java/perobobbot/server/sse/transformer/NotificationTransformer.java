package perobobbot.server.sse.transformer;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UserService;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.Notification;
import perobobbot.server.sse.SSEventAccess;

@Component
public class NotificationTransformer extends BaseEventTransformer<Notification> {


    private final @NonNull UserService userService;

    public NotificationTransformer(@NonNull @EventService UserService userService, @NonNull DefaultPayloadConstructor payloadConstructor) {
        super(payloadConstructor, Notification.class);
        this.userService = userService;
    }

    @Override
    protected @NonNull String getEventName() {
        return "notification";
    }

    @Override
    protected @NonNull SSEventAccess getAuthorizedLogins(@NonNull Notification event) {
        return event.getOwner()
                    .map(this::getBotUserFromIdentityInfo)
                    .map(SSEventAccess::fromSetOfAuthorizedLogins)
                    .orElse(SSEventAccess.PERMIT_ALL);
    }

    private @NonNull ImmutableSet<String> getBotUserFromIdentityInfo(@NonNull IdentityInfo info) {
        return userService.findLoginOfUsersAuthenticatedWithViewerId(info.getPlatform(), info.getViewerId());
    }


}
