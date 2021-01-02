package perobobbot.lang;

import lombok.NonNull;

public interface PasswordEncoder {

    @NonNull String encode(@NonNull CharSequence password);
}
