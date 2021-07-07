package perobobbot.twitch.client.api.channelpoints;

import lombok.*;
import perobobbot.twitch.api.Pagination;
import perobobbot.twitch.api.RewardRedemptionStatus;

import java.util.Optional;

@Value
@Getter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class GetCustomRewardRedemptionParameter {

    @Getter
    @NonNull String rewardId;

    String id;
    RewardRedemptionStatus status;
    SortOrder sort;
    String after;
    Integer first;

    public @NonNull  Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public @NonNull  Optional<RewardRedemptionStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public @NonNull  Optional<SortOrder> getSort() {
        return Optional.ofNullable(sort);
    }

    public @NonNull  Optional<String> getAfter() {
        return Optional.ofNullable(after);
    }

    public @NonNull  Optional<Integer> getFirst() {
        return Optional.ofNullable(first);
    }


    public GetCustomRewardRedemptionParameter createNextPage(@NonNull Pagination pagination) {
        return toBuilder().after(pagination.getCursor()).build();
    }
}
