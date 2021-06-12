package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum SubscriptionStatus implements IdentifiedEnum {
    ENABLED("enabled"),
    WEBHOOK_CALLBACK_VERIFICATION_PENDING("webhook_callback_verification_pending"),
    WEBHOOK_CALLBACK_VERIFICATION_FAILED("webhook_callback_verification_failed"),
    NOTIFICATION_FAILURES_EXCEEDED("notification_failures_exceeded"),
    AUTHORIZATION_REVOKED("authorization_revoked"),
    USER_REMOVED("user_removed"),
    ;

    @Getter
    private final @NonNull String identification;
}
