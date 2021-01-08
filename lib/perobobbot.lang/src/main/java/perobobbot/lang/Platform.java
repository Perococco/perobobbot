package perobobbot.lang;

import lombok.NonNull;

public enum Platform implements IdentifiedEnum {
    TWITCH,
    LOCAL,
    ;

    public boolean isLocal() {
        return LOCAL == this;
    }


    @Override
    public @NonNull String getIdentification() {
        return name();
    }
}
