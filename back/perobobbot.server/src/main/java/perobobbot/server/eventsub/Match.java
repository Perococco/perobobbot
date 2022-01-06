package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.*;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.SubscriptionData;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Builder
public class Match {

    @Singular
    private final @NonNull ImmutableList<String> toRevokeSubs;
    @Singular
    private final @NonNull ImmutableMap<UUID, SubscriptionIdentity> toUpdateSubs;
    @Singular
    private final @NonNull ImmutableMap<UUID, SubscriptionData> toReplaces;
    @Singular
    private final @NonNull ImmutableList<SubscriptionView> toRefreshSubs;

    public boolean hasAnyChange() {
        return !toRevokeSubs.isEmpty() || !toUpdateSubs.isEmpty() || !toRefreshSubs.isEmpty();
    }
}
