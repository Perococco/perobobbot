package perobobbot.server;

import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        new PasswordEncoderTest().encode("admin");
    }

    private void encode(@NonNull String password) {
        System.out.printf("'%s'  --> '%s'%n%n", password, passwordEncoder.encode(password));
    }
}
