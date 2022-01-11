package perobobbot.lang;

import lombok.NonNull;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author perococco
 */
public enum RandomString {

    INSTANCE,
    ;

    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789)=".toCharArray();


    private final Random random = new SecureRandom();

    public static @NonNull String createWithLength(int length) {
        return INSTANCE.generate(length);
    }

    public @NonNull  String generate(int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(nextRandomChar());
        }
        return sb.toString();
    }

    private char nextRandomChar() {
        return CHARS[random.nextInt(CHARS.length)];
    }


}
