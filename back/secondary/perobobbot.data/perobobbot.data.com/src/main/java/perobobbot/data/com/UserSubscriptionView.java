package perobobbot.data.com;

import lombok.NonNull;

import java.util.UUID;

public record UserSubscriptionView(@NonNull UUID id,
                                   @NonNull String login,
                                   @NonNull SubscriptionView subscription) {


}
