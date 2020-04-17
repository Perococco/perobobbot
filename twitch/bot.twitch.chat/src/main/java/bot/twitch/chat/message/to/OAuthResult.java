package bot.twitch.chat.message.to;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author perococco
 **/
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthResult {

    private static final OAuthResult SUCCESS = new OAuthResult("");

    @NonNull
    public static OAuthResult success() {
        return SUCCESS;
    }

    @NonNull
    public static OAuthResult failure(@NonNull String failureMessage) {
        return new OAuthResult(failureMessage);
    }

    @NonNull
    private String failureMessage;

    public boolean isSuccess() {
        return failureMessage.isEmpty();
    }

    public boolean isFailure() {
        return !isSuccess();
    }

}
