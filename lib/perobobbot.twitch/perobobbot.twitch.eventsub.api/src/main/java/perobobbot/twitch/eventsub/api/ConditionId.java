package perobobbot.twitch.eventsub.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConditionId implements CharSequence {

    private final String id;

    public ConditionId(@NonNull ImmutableMap<CriteriaType, String> conditions) {
        this.id = conditions.entrySet()
                            .stream()
                            .sorted(Comparator.comparing(e -> e.getKey().getIdentification()))
                            .map(e -> e.getKey() + ":" + e.getValue())
                            .collect(Collectors.joining(" "));
    }

    @Override
    public int length() {
        return id.length();
    }

    @Override
    public char charAt(int index) {
        return id.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return id.subSequence(start,end);
    }

    @Override
    public String toString() {
        return id;
    }
}
