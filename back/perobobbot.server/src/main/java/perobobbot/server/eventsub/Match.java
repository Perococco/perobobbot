package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.*;
import perobobbot.data.com.SubscriptionView;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Builder
public class Match {

    @Singular
    private final @NonNull ImmutableList<String> toRevokeSubs;
    @Singular
    private final @NonNull ImmutableMap<UUID,String> toUpdateSubs;
    @Singular
    private final @NonNull ImmutableList<SubscriptionView> toRefreshSubs;
}
