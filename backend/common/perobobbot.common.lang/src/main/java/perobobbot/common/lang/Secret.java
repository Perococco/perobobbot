package perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class Secret {

    @Getter
    private final String value;

    @NonNull
    public Secret append(@NonNull Secret secret) {
        return new Secret(this.value+secret.value);
    }

    @NonNull
    public Secret append(@NonNull String value) {
        return new Secret(this.value+value);
    }

    @NonNull
    public Secret prepend(@NonNull String value) {
        return new Secret(value+this.value);
    }

    @Override
    public String toString() {
        return "********";
    }
}