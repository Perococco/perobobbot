package perobobbot.http;

import lombok.*;

import java.util.Optional;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<V,P> {

    public static <V,P> Page<V,P> withNext(@NonNull V value, @NonNull P nextParameter) {
        return new Page<>(value,nextParameter);
    }

    public static <V,P> Page<V,P> withoutNext(@NonNull V value) {
        return new Page<>(value,null);
    }

    @Getter
    private final @NonNull V value;
    private final P nextParameters;

    @NonNull Optional<P> getNextParameters() {
        return Optional.ofNullable(nextParameters);
    }

}
