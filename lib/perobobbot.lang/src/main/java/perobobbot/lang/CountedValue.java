package perobobbot.lang;

import com.google.common.base.Function;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.fp.Function1;

@Value
public class CountedValue<T> {

    @NonNull T value;

    int number;

    public @NonNull <U> CountedValue<U> map(@NonNull Function1<? super T, ? extends U> mapper) {
        return new CountedValue<>(mapper.apply(value),number);
    }
}
