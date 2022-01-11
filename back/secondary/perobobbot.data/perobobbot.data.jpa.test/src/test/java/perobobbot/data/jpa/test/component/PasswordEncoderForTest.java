package perobobbot.data.jpa.test.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.PasswordEncoder;

@Component
public class PasswordEncoderForTest implements PasswordEncoder {

    @Override
    public @NonNull String encode(@NonNull CharSequence password) {
        return "E_"+password.toString();
    }
}
