package perobobbot.lang;

import lombok.NonNull;

public class StringTools {

    public static boolean isAlphaNumeric(int chr) {
        return (chr>='0' && chr<='9') || (chr>='a' && chr<='z') || (chr >= 'A' && chr <= 'Z');
    }

    public static boolean isAlphaNumeric(@NonNull String str) {
        return str.chars().allMatch(StringTools::isAlphaNumeric);
    }

    public static boolean hasData(@NonNull String str) {
        return !str.isEmpty() && !str.isBlank();
    }
}
