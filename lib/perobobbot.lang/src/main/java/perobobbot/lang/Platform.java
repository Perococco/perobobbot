package perobobbot.lang;

public enum Platform {
    TWITCH,
    LOCAL,
    ;

    private boolean isLocal() {
        return LOCAL == this;
    }

}
