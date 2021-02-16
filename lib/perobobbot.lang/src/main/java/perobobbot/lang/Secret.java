package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@EqualsAndHashCode(of = "value")
public class Secret {

    @Getter
    private final String value;

    public static Secret empty() {
        return new Secret("")
;    }

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

    public boolean hasData() {
        return StringTools.hasData(value);
    }

    public static @NonNull Secret of(@NonNull String value) {
        return new Secret(value);
    }


    public boolean isBlank() {
        return value.isBlank();
    }
}
