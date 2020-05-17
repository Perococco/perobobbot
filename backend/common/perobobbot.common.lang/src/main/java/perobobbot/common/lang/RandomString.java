package perobobbot.common.lang;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

/**
 * @author Bastien Aracil
 * @version 15/04/2019
 */
public class RandomString {

    public static String generate(int length) {
        return INSTANCE.doGenerate(length);
    }


    private static final RandomString INSTANCE = new RandomString();


    private final Random random = new SecureRandom();

    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789)=+@".toCharArray();

    private String doGenerate(int length) {
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
