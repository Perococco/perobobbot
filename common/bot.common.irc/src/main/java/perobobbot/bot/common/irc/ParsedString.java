package perobobbot.bot.common.irc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class ParsedString {

    @NonNull
    private final String reference;

    private int idx = 0;

    public boolean isEmpty() {
        return idx >= reference.length();
    }

    public ParsedString moveBy(int nbCharacters) {
        this.idx+=nbCharacters;
        return this;
    }

    public int lastIndexOf(@NonNull String value) {
        return Math.max(-1,reference.lastIndexOf(value)-idx);
    }

    public String extractUpTo(int firstIndexNotIncluded) {
        if (firstIndexNotIncluded<=0) {
            return "";
        }
        final String result = reference.substring(idx,idx+firstIndexNotIncluded);
        this.idx += firstIndexNotIncluded;
        return result;
    }

    public ParsedString moveByStringLength(String string) {
        return moveBy(string.length());
    }

    public boolean startsWith(@NonNull String prefix) {
        return reference.startsWith(prefix, idx);
    }

    public String extractToNextSpace() {
        final int spaceIndex = reference.indexOf(' ',idx);
        if (spaceIndex<0) {
            throw new IllegalArgumentException("No space from position '"+idx+"' in '"+reference+"'");
        }
        final String result = reference.substring(idx,spaceIndex);
        this.idx = spaceIndex+1;
        return result;
    }

    @NonNull
    public String extractToEndOfString() {
        final String result = reference.substring(idx);
        this.idx = reference.length();
        return result;
    }

    @Override
    public String toString() {
        return "ParsedString{" +
               "reference='" + reference + '\'' +
               ", idx=" + idx +
               '}';
    }

    public int indexOf(String s) {
        return Math.max(-1,s.indexOf(s,idx)-idx);
    }
}
