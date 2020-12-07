package perobobbot.lang;

import lombok.NonNull;

public class EllipsedString implements CharSequence {

    public static @NonNull CharSequence create(@NonNull String string, int maxSize, @NonNull String ellipsed) {
        if (string.length()<=maxSize) {
            return string;
        }
        return new EllipsedString(string, maxSize, ellipsed);
    }

    public static @NonNull CharSequence create(@NonNull String string, int maxSize) {
        return create(string,maxSize,"…");
    }

    private final String ellipsed;

    private EllipsedString(@NonNull String string, int maxSize, @NonNull String ellipse) {
        if (maxSize<ellipse.length()) {
            throw new IllegalArgumentException("Invalid maxSize: '"+maxSize+"' with ellipse:'"+ellipse+"'");
        } else if (ellipse.isEmpty()) {
            throw new IllegalArgumentException("Invalid ellipse. Should at least contain one character");
        }
        if (string.length()<=maxSize) {
            this.ellipsed = string;
        } else {
            this.ellipsed = string.substring(0,maxSize-ellipse.length())+ellipse;
        }
    }

    public EllipsedString(@NonNull String string, int maxSize) {
        this(string,maxSize,"…");
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public String toString() {
        return ellipsed;
    }
}
