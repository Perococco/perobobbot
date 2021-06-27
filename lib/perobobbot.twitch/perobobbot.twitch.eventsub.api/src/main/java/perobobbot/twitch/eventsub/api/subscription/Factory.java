package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.eventsub.api.CriteriaType;

@RequiredArgsConstructor
public class Factory {

    private final @NonNull CriteriaType criteriaType;

    private final @NonNull Function1<? super String, ? extends Subscription> factory;

    public @NonNull Subscription create(@NonNull ImmutableMap<String,String> condition) {
        final var value = new ConditionHelper(condition).get(criteriaType);
        return factory.f(value);
    }

}
