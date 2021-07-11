package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.fp.Function1;

import java.util.HashMap;
import java.util.Map;

@Value
public class Conditions {

    @Getter(AccessLevel.NONE)
    @NonNull ImmutableMap<String, String> values;

    public Conditions(@NonNull ImmutableMap<String, String> values) {
        this.values = values.entrySet()
                                        .stream()
                                        .filter(e -> !e.getValue().isEmpty())
                                        .collect(MapTool.entryCollector());
    }

    public <T> @NonNull ImmutableMap<T,String> toMap(@NonNull Function1<? super String, ? extends T> keyMapper) {
        return values.keySet().stream().collect(ImmutableMap.toImmutableMap(keyMapper, values::get));
    }

    public @NonNull Map<String, String> copyAsMap() {
        return new HashMap<>(values);
    }




    public static @NonNull Conditions with(
            @NonNull IdentifiedEnum key1, @NonNull String value1) {
        return new Conditions(ImmutableMap.of(
                key1.getIdentification(), value1
        ));
    }

    public static @NonNull Conditions with(
            @NonNull IdentifiedEnum key1, @NonNull String value1,
            @NonNull IdentifiedEnum key2, @NonNull String value2) {
        return new Conditions(ImmutableMap.of(
                key1.getIdentification(), value1,
                key2.getIdentification(), value2
        ));
    }

    public static @NonNull Conditions with(
            @NonNull IdentifiedEnum key1, @NonNull String value1,
            @NonNull IdentifiedEnum key2, @NonNull String value2,
            @NonNull IdentifiedEnum key3, @NonNull String value3) {
        return new Conditions(ImmutableMap.of(
                key1.getIdentification(), value1,
                key2.getIdentification(), value2,
                key3.getIdentification(), value3
        ));
    }

    public static @NonNull Conditions with(@NonNull Map<String, String> condition) {
        return new Conditions(ImmutableMap.copyOf(condition));
    }

    public static @NonNull Conditions withIdentifiedEnumAsKey(@NonNull Map<? extends IdentifiedEnum, String> conditions) {
        return new Conditions(conditions.keySet().stream().collect(ImmutableMap.toImmutableMap(IdentifiedEnum::getIdentification, conditions::get)));
    }

}
