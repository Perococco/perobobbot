package perobobbot.lang;

public enum Platform {
    TWITCH,
    LOCAL,
    ;

    public boolean isLocal() {
        return LOCAL == this;
    }

}
