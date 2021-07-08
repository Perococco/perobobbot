package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.lang.fp.Function1;

import java.util.Map;

@Value
@Builder
public class Conditions {

    @Singular
    @NonNull ImmutableMap<String,String> values;

    public <T> @NonNull ImmutableMap<T,String> toMap(@NonNull Function1<? super String, ? extends T> keyMapper) {
        return values.keySet().stream().collect(ImmutableMap.toImmutableMap(keyMapper, values::get));
    }


    public static @NonNull Conditions with(@NonNull Map<String, String> condition) {
        return new Conditions(ImmutableMap.copyOf(condition));
    }

    public static @NonNull Conditions withIdentifiedEnumAsKey(@NonNull Map<? extends IdentifiedEnum, String> conditions) {
        return new Conditions(conditions.keySet().stream().collect(ImmutableMap.toImmutableMap(IdentifiedEnum::getIdentification,conditions::get)));
    }


}
