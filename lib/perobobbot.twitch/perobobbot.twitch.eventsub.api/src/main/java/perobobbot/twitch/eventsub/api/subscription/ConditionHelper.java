package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.eventsub.api.CriteriaType;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ConditionHelper {

    private final @NonNull ImmutableMap<String,String> condition;

    public @NonNull String get(@NonNull CriteriaType criteriaType) {
        return Objects.requireNonNull(
                condition.get(criteriaType.getIdentification()),
                "Criteria "+criteriaType+" is required");

    }

    public @NonNull Optional<String> find(@NonNull CriteriaType criteriaType) {
        return Optional.ofNullable(condition.get(criteriaType.getIdentification()));
    }

}
