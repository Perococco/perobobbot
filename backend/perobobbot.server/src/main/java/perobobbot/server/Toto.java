package perobobbot.server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Toto {

    public static void main(String[] args) {
        final PasswordEncoder p = new  BCryptPasswordEncoder();
        System.out.println(p.encode("admin"));
    }
}
