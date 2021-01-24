package perobobbot.lang;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Theme {

    public static final Theme EMPTY = new Theme("-","");

    @NonNull
    public static Theme create(@NonNull String name, @NonNull String themeUrl) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid theme name '"+name+"'");
        }
        return new Theme(name,themeUrl);
    }

    @NonNull String name;

    @NonNull String themeUrl;

    public boolean isEmpty() {
        return themeUrl.isEmpty();
    }

    @Override
    public String toString() {
        return isEmpty()?"Default":name;
    }
}
