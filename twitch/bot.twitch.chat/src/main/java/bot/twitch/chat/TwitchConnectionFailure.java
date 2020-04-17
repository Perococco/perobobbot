package bot.twitch.chat;

public class TwitchConnectionFailure extends RuntimeException {

    public TwitchConnectionFailure(Throwable cause) {
        super(cause);
    }
}
