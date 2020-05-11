package perobobbot.twitch.chat.message.to;

import perobobbot.common.lang.fp.Either;
import perobobbot.twitch.chat.message.from.GlobalUserState;
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

    @NonNull
    private final Either<String, GlobalUserState> either;

    @NonNull
    public static OAuthResult success(@NonNull GlobalUserState state) {
        return new OAuthResult(Either.right(state));
    }

    @NonNull
    public static OAuthResult failure(@NonNull String failureMessage) {
        return new OAuthResult(Either.left(failureMessage));
    }


    public boolean isSuccess() {
        return either.isRight();
    }

    public boolean isFailure() {
        return !isSuccess();
    }

}
