package perococco.perobobbot.common.irc;

import lombok.NonNull;

/**
 * @author perococco
 **/
public class TagValueUnescaper {

    public static final char ESCAPING_CHAR = '\\';

    @NonNull
    public String unescape(@NonNull String value) {
        if (value.indexOf(ESCAPING_CHAR)<0) {
            return value;
        }

        final StringBuilder sb = new StringBuilder();

        boolean previousIsEscapingChar = false;

        for (int i = 0; i < value.length(); i++) {

            final char currentChar = value.charAt(i);
            final boolean currentIsEscapingChar = currentChar == ESCAPING_CHAR;

            if (currentIsEscapingChar && !previousIsEscapingChar) {
                previousIsEscapingChar = true;
            } else {
                final char c = previousIsEscapingChar?unescaped(currentChar):currentChar;
                sb.append(c);
                previousIsEscapingChar = false;
            }
        }

        return sb.toString();
    }

    public char unescaped(char escaped) {
        switch (escaped) {
            case ':' : return ';';
            case 's' : return ' ';
            case 'r' : return '\r';
            case 'n' : return '\n';
            default:
                return escaped;
        }
    }
}
